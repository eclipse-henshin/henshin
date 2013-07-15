/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.giraph.examples;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.apache.giraph.aggregators.LongSumAggregator;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.master.DefaultMasterCompute;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

/**
 * Generated implementation of the Henshin unit "StarMain".
 */
@Algorithm(
    name = "StarMain"
)
public class StarMain extends
  BasicComputation<HenshinUtil.VertexId, ByteWritable,
  ByteWritable, HenshinUtil.Match> {

  /**
   * Name of the rule application count aggregator.
   */
  public static final String AGGREGATOR_RULE_APPLICATIONS = "ruleApps";

  /**
   * Name of the node generation aggregator.
   */
  public static final String AGGREGATOR_NODE_GENERATION = "nodeGen";

  /**
   * Name of the application stack aggregator.
   */
  public static final String AGGREGATOR_APPLICATION_STACK = "appStack";

  /**
   * Type constant for "Vertex".
   */
  public static final ByteWritable TYPE_VERTEX
    = new ByteWritable((byte) 0);

  /**
   * Type constant for "left".
   */
  public static final ByteWritable TYPE_VERTEX_LEFT
    = new ByteWritable((byte) 1);

  /**
   * Type constant for "conn".
   */
  public static final ByteWritable TYPE_VERTEX_CONN
    = new ByteWritable((byte) 2);

  /**
   * Type constant for "right".
   */
  public static final ByteWritable TYPE_VERTEX_RIGHT
    = new ByteWritable((byte) 3);

  /**
   * Type constant for "VertexContainer".
   */
  public static final ByteWritable TYPE_VERTEX_CONTAINER
    = new ByteWritable((byte) 4);

  /**
   * Type constant for "vertices".
   */
  public static final ByteWritable TYPE_VERTEX_CONTAINER_VERTICES
    = new ByteWritable((byte) 5);

  /**
   * Unit constant for "StarMain".
   */
  public static final int UNIT_STAR_MAIN = 0;

  /**
   * Unit constant for "ExtendStarLoop".
   */
  public static final int UNIT_EXTEND_STAR_LOOP = 1;

  /**
   * Rule constant for "DeleteStar".
   */
  public static final int RULE_DELETE_STAR = 2;

  /**
   * Rule constant for "ExtendStar".
   */
  public static final int RULE_EXTEND_STAR = 3;

  /**
   * Logging support.
   */
  protected static final Logger LOG = Logger.getLogger(StarMain.class);

  /*
   * (non-Javadoc)
   * @see org.apache.giraph.graph.Computation#compute(
   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)
   */
  @Override
  public void compute(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches) throws IOException {

    // Get the current application stack:
    HenshinUtil.ApplicationStack stack =
      getAggregatedValue(AGGREGATOR_APPLICATION_STACK);

    // Check if we should stop:
    if (stack.getStackSize() == 0) {
      long ruleApps = ((LongWritable)
        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();
      if (ruleApps == 0) {
        vertex.voteToHalt();
      }
      return;
    }

    // Get the last step:
    int rule = stack.getLastUnit();
    int microstep = stack.getLastMicrostep();

    // Log execution info
    long superstep = getSuperstep();
    LOG.info("Vertex " + vertex.getId() + " in superstep " + superstep +
      " executing rule " + rule + " in microstep " + microstep);
    for (HenshinUtil.Match match : matches) {
      LOG.info("Vertex " + vertex.getId() +
        " received (partial) match " + match);
    }

    // Find out which rule to apply:
    switch (rule) {
    case RULE_EXTEND_STAR:
      matchExtendStar(vertex, matches, microstep);
      break;
    case RULE_DELETE_STAR:
      matchDeleteStar(vertex, matches, microstep);
      break;
    default:
      throw new RuntimeException("Unknown rule: " + rule);
    }
  }

  /**
   * Match (and apply) the rule "ExtendStar".
   * This takes 2 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchExtendStar(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches,
      int microstep) throws IOException {

    if (microstep == 0) {

      // Matching node "a". Type must be "VertexContainer":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX_CONTAINER.get();
      if (ok) {
        // Create a new partial match:
        HenshinUtil.Match match =
          new HenshinUtil.Match().append(vertex.getId());
        // Send a match request to all outgoing edges of type "vertices":
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_CONTAINER_VERTICES.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      } // end if ok

    } else if (microstep == 1) {

      // Matching node "b". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.append(vertex.getId());
          // Apply the rule:
          applyExtendStar(vertex, match);
        }
      } // end if ok

    }
  }

  /**
   * Apply the rule ExtendStarto a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applyExtendStar(
    Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex,
    HenshinUtil.Match match) throws IOException {

    LOG.info("Vertex " + vertex.getId() +
      " applying rule ExtendStar with match " + match);

    HenshinUtil.VertexId cur1 = match.getVertexId(1);

    // Create vertex "c":
    HenshinUtil.VertexId new0 =
      deriveVertexId(vertex.getId(), (byte) 0);
    addVertexRequest(new0, TYPE_VERTEX);

    // Create edge "b" -> "c":
    HenshinUtil.VertexId src0 = cur1;
    HenshinUtil.VertexId trg0 = new0;
    Edge<HenshinUtil.VertexId, ByteWritable> edge0 =
      EdgeFactory.create(trg0, TYPE_VERTEX_LEFT);
    addEdgeRequest(src0, edge0);

    // Update the statistics:
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));

  }

  /**
   * Match (and apply) the rule "DeleteStar".
   * This takes 3 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchDeleteStar(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches,
      int microstep) throws IOException {

    if (microstep == 0) {

      // Matching node "a". Type must be "VertexContainer":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX_CONTAINER.get();
      if (ok) {
        // Create a new partial match:
        HenshinUtil.Match match =
          new HenshinUtil.Match().append(vertex.getId());
        // Send a match request to all outgoing edges of type "vertices":
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_CONTAINER_VERTICES.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      } // end if ok

    } else if (microstep == 1) {

      // Matching node "b". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.append(vertex.getId());
          // Send a match request to all outgoing edges of type "left":
          for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
            vertex.getEdges()) {
            if (edge.getValue().get() ==
              TYPE_VERTEX_LEFT.get()) {
              LOG.info("Vertex " + vertex.getId() +
                " sending (partial) match " + match +
                " to vertex " + edge.getTargetVertexId());
              sendMessage(edge.getTargetVertexId(), match);
            }
          }
        }
      } // end if ok

    } else if (microstep == 2) {

      // Matching node "c". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.append(vertex.getId());
          // Apply the rule:
          applyDeleteStar(vertex, match);
        }
      } // end if ok

    }
  }

  /**
   * Apply the rule DeleteStarto a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applyDeleteStar(
    Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex,
    HenshinUtil.Match match) throws IOException {

    LOG.info("Vertex " + vertex.getId() +
      " applying rule DeleteStar with match " + match);

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);
    HenshinUtil.VertexId cur2 = match.getVertexId(2);

    // Remove edge "a" -> "b":
    removeEdgesRequest(cur0, cur1);

    // Remove edge "b" -> "c":
    removeEdgesRequest(cur1, cur2);

    // Remove vertex "b":
    removeVertexRequest(cur1);

    // Remove vertex "c":
    removeVertexRequest(cur2);

    // Update the statistics:
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));

  }

  /**
   * Derive a new vertex Id from an exiting one.
   * @param baseId The base vertex Id.
   * @param vertexIndex The relative index of the new vertex.
   * @return The derived vertex Id.
   */
  private HenshinUtil.VertexId deriveVertexId(
    HenshinUtil.VertexId baseId, int vertexIndex) {
    long generation = ((LongWritable) getAggregatedValue(
        AGGREGATOR_NODE_GENERATION)).get();
    return baseId.append((byte) generation).append((byte) vertexIndex);
  }

  /**
   * Master compute which registers and updates the required aggregators.
   */
  public static class MasterCompute extends DefaultMasterCompute {

    /**
     * Stack for storing the execution orders of independent units.
     */
    private final Deque<List<Integer>> independentUnitOrders =
      new ArrayDeque<List<Integer>>();

    @Override
    public void compute() {

      // Get the number of rule applications in the last superstep:
      long ruleApps = ((LongWritable)
        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();
      if (getSuperstep() > 0) {
        LOG.info(ruleApps + " rule applications in superstep " +
          (getSuperstep() - 1));
      }
      if (ruleApps > 0) {
        long nodeGen = ((LongWritable)
          getAggregatedValue(AGGREGATOR_NODE_GENERATION)).get();
        setAggregatedValue(AGGREGATOR_NODE_GENERATION,
          new LongWritable(nodeGen + 1));
      }

      // Update the application stack:
      HenshinUtil.ApplicationStack stack;
      if (getSuperstep() == 0) {
        stack = new HenshinUtil.ApplicationStack();
        stack = stack.append(UNIT_STAR_MAIN, 0);
        stack = nextRuleStep(stack, ruleApps);
      } else {
        stack = getAggregatedValue(AGGREGATOR_APPLICATION_STACK);
        stack = nextRuleStep(stack, ruleApps);
      }
      setAggregatedValue(AGGREGATOR_APPLICATION_STACK, stack);

    }

    /**
     * Compute the next rule application stack.
     * @param stack Current application stack.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack nextRuleStep(
      HenshinUtil.ApplicationStack stack, long ruleApps) {

      // Iteratively update the application stack:
      while (stack.getStackSize() > 0) {
        int unit = stack.getLastUnit();
        int microstep = stack.getLastMicrostep();
        stack = stack.removeLast();
        switch (unit) {
        case UNIT_STAR_MAIN:
          switch (microstep) {
          case 0:
            stack = stack.append(UNIT_STAR_MAIN, 1);
            stack = stack.append(UNIT_EXTEND_STAR_LOOP, 0);
            break;
          case 1:
            stack = stack.append(UNIT_STAR_MAIN, 2);
            stack = stack.append(RULE_DELETE_STAR, 0);
            break;
          break;
        case UNIT_EXTEND_STAR_LOOP:
          if (microstep < 5) {
            stack = stack.append(UNIT_EXTEND_STAR_LOOP, microstep + 1);
            stack = stack.append(RULE_EXTEND_STAR, 0);
          }
          break;
        case RULE_DELETE_STAR:
          if (microstep < 3) {
            stack = stack.append(RULE_DELETE_STAR, microstep + 1);
          }
          break;
        case RULE_EXTEND_STAR:
          if (microstep < 2) {
            stack = stack.append(RULE_EXTEND_STAR, microstep + 1);
          }
          break;
        default:
          throw new RuntimeException("Unknown unit " + unit);
        }

        // If the last unit is a rule, we can stop:
        if (stack.getStackSize() > 0) {
          unit = stack.getLastUnit();
          if (unit == RULE_EXTEND_STAR ||
            unit == RULE_DELETE_STAR) {
            break;
          }
        }
      }
      return stack;
    }

    @Override
    public void initialize() throws InstantiationException,
        IllegalAccessException {
      registerAggregator(AGGREGATOR_RULE_APPLICATIONS,
        LongSumAggregator.class);
      registerPersistentAggregator(AGGREGATOR_NODE_GENERATION,
        LongSumAggregator.class);
      registerPersistentAggregator(AGGREGATOR_APPLICATION_STACK,
        HenshinUtil.ApplicationStackAggregator.class);
    }

  }
}
