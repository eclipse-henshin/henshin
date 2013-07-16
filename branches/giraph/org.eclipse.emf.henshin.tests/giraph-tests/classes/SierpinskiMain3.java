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
 * Generated implementation of the Henshin unit "SierpinskiMain".
 */
@Algorithm(
    name = "SierpinskiMain"
)
public class SierpinskiMain3 extends
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
   * Unit constant for "SierpinskiMain".
   */
  public static final int UNIT_SIERPINSKI_MAIN = 0;

  /**
   * Rule constant for "Sierpinski".
   */
  public static final int RULE_SIERPINSKI = 1;

  /**
   * Logging support.
   */
  protected static final Logger LOG = Logger.getLogger(SierpinskiMain3.class);

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

    // Find out which rule to apply:
    switch (rule) {
    case RULE_SIERPINSKI:
      matchSierpinski(vertex, matches, microstep);
      break;
    default:
      throw new RuntimeException("Unknown rule: " + rule);
    }
  }

  /**
   * Match (and apply) the rule "Sierpinski".
   * This takes 4 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchSierpinski(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches,
      int microstep) throws IOException {

    LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
      " matching rule Sierpinski in microstep " + microstep);
    for (HenshinUtil.Match match : matches) {
      LOG.info("Vertex " + vertex.getId() +
        " received (partial) match " + match);
    }

    if (microstep == 0) {

      // Matching node "a". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX.get();
      if (ok) {
        // Create a new partial match:
        HenshinUtil.Match match =
          new HenshinUtil.Match().append(vertex.getId());
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
      } // end if ok

    } else if (microstep == 1) {

      // Matching node "b". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        TYPE_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.append(vertex.getId());
          // Send a match request to all outgoing edges of type "conn":
          for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
            vertex.getEdges()) {
            if (edge.getValue().get() ==
              TYPE_VERTEX_CONN.get()) {
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
          // Send the message back to matches of node "a":
          for (HenshinUtil.Match m : matches) {
            HenshinUtil.VertexId targetVertexId =
              m.getVertexId(0);
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " to vertex " + targetVertexId);
            sendMessage(targetVertexId, match);
          }
        }
      } // end if ok

    } else if (microstep == 3) {

      // Node "a": check for edge to match of "c" of type "right":
      for (HenshinUtil.Match match : matches) {
        HenshinUtil.VertexId targetId = match.getVertexId(2);
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_RIGHT.get() &&
            edge.getTargetVertexId().equals(targetId)) {
            // Apply the rule:
            applySierpinski(vertex, match);
          }
        }
      }


    } else {
      throw new RuntimeException("Illegal microstep for rule " +
        "Sierpinski: " + microstep);
    }
  }

  /**
   * Apply the rule "Sierpinski" to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applySierpinski(
    Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex,
    HenshinUtil.Match match) throws IOException {

    LOG.info("Vertex " + vertex.getId() +
      " applying rule Sierpinski with match " + match);

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);
    HenshinUtil.VertexId cur2 = match.getVertexId(2);

    // Remove edge "a" -> "b":
    removeEdgesRequest(cur0, cur1);

    // Remove edge "a" -> "c":
    removeEdgesRequest(cur0, cur2);

    // Remove edge "b" -> "c":
    removeEdgesRequest(cur1, cur2);

    // Create vertex 3:
    HenshinUtil.VertexId new0 =
      deriveVertexId(vertex.getId(), (byte) 0);
    addVertexRequest(new0, TYPE_VERTEX);

    // Create vertex 4:
    HenshinUtil.VertexId new1 =
      deriveVertexId(vertex.getId(), (byte) 1);
    addVertexRequest(new1, TYPE_VERTEX);

    // Create vertex 5:
    HenshinUtil.VertexId new2 =
      deriveVertexId(vertex.getId(), (byte) 2);
    addVertexRequest(new2, TYPE_VERTEX);

    // Create edge "a" -> 3:
    HenshinUtil.VertexId src0 = cur0;
    HenshinUtil.VertexId trg0 = new0;
    Edge<HenshinUtil.VertexId, ByteWritable> edge0 =
      EdgeFactory.create(trg0, TYPE_VERTEX_LEFT);
    addEdgeRequest(src0, edge0);

    // Create edge "a" -> 4:
    HenshinUtil.VertexId src1 = cur0;
    HenshinUtil.VertexId trg1 = new1;
    Edge<HenshinUtil.VertexId, ByteWritable> edge1 =
      EdgeFactory.create(trg1, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src1, edge1);

    // Create edge "b" -> 5:
    HenshinUtil.VertexId src2 = cur1;
    HenshinUtil.VertexId trg2 = new2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge2 =
      EdgeFactory.create(trg2, TYPE_VERTEX_CONN);
    addEdgeRequest(src2, edge2);

    // Create edge 3 -> "b":
    HenshinUtil.VertexId src3 = new0;
    HenshinUtil.VertexId trg3 = cur1;
    Edge<HenshinUtil.VertexId, ByteWritable> edge3 =
      EdgeFactory.create(trg3, TYPE_VERTEX_LEFT);
    addEdgeRequest(src3, edge3);

    // Create edge 3 -> 4:
    HenshinUtil.VertexId src4 = new0;
    HenshinUtil.VertexId trg4 = new1;
    Edge<HenshinUtil.VertexId, ByteWritable> edge4 =
      EdgeFactory.create(trg4, TYPE_VERTEX_CONN);
    addEdgeRequest(src4, edge4);

    // Create edge 3 -> 5:
    HenshinUtil.VertexId src5 = new0;
    HenshinUtil.VertexId trg5 = new2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge5 =
      EdgeFactory.create(trg5, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src5, edge5);

    // Create edge 4 -> 5:
    HenshinUtil.VertexId src6 = new1;
    HenshinUtil.VertexId trg6 = new2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge6 =
      EdgeFactory.create(trg6, TYPE_VERTEX_LEFT);
    addEdgeRequest(src6, edge6);

    // Create edge 4 -> "c":
    HenshinUtil.VertexId src7 = new1;
    HenshinUtil.VertexId trg7 = cur2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge7 =
      EdgeFactory.create(trg7, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src7, edge7);

    // Create edge 5 -> "c":
    HenshinUtil.VertexId src8 = new2;
    HenshinUtil.VertexId trg8 = cur2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge8 =
      EdgeFactory.create(trg8, TYPE_VERTEX_CONN);
    addEdgeRequest(src8, edge8);

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
     * Stack for storing unit success flags.
     */
    private final Deque<Boolean> unitSuccesses =
      new ArrayDeque<Boolean>();

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
        stack = stack.append(UNIT_SIERPINSKI_MAIN, 0);
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
        case UNIT_SIERPINSKI_MAIN:
          stack = processSierpinskiMain(
            stack, microstep, ruleApps);
          break;
        case RULE_SIERPINSKI:
          stack = processSierpinski(
            stack, microstep, ruleApps);
          break;
        default:
          throw new RuntimeException("Unknown unit " + unit);
        }
        // If the last unit is a rule, we can stop:
        if (stack.getStackSize() > 0) {
          unit = stack.getLastUnit();
          if (unit == RULE_SIERPINSKI) {
            break;
          }
        }
      }
      return stack;
    }

   /**
     * Process IteratedUnit "SierpinskiMain".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processSierpinskiMain(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep > 0 && !unitSuccesses.pop()) {
        unitSuccesses.push(false); // no success
      } else if (microstep == 3) {
        unitSuccesses.push(true); // success
      } else if (microstep < 3) {
        stack = stack.append(UNIT_SIERPINSKI_MAIN, microstep + 1);
        stack = stack.append(RULE_SIERPINSKI, 0);
      }
      return stack;
    }

   /**
     * Process Rule "Sierpinski".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processSierpinski(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep < 3) {
        stack = stack.append(RULE_SIERPINSKI, microstep + 1);
      } else {
        unitSuccesses.push(ruleApps > 0);
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
