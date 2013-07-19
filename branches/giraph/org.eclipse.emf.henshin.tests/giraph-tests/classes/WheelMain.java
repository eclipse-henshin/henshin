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
import static org.apache.giraph.examples.HenshinUtil
  .ApplicationStack;
import static org.apache.giraph.examples.HenshinUtil
  .ApplicationStackAggregator;
import static org.apache.giraph.examples.HenshinUtil
  .Match;
import static org.apache.giraph.examples.HenshinUtil
  .VertexId;

/**
 * Generated implementation of the Henshin unit "WheelMain".
 */
@Algorithm(
    name = "WheelMain"
)
public class WheelMain extends
  BasicComputation<VertexId, ByteWritable, ByteWritable, Match> {

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
   * Unit constant for "WheelMain".
   */
  public static final int UNIT_WHEEL_MAIN = 0;

  /**
   * Rule constant for "Wheel".
   */
  public static final int RULE_WHEEL = 1;

  /**
   * Logging support.
   */
  protected static final Logger LOG = Logger.getLogger(WheelMain.class);

  /*
   * (non-Javadoc)
   * @see org.apache.giraph.graph.Computation#compute(
   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)
   */
  @Override
  public void compute(
      Vertex<VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<Match> matches) throws IOException {
    ApplicationStack stack =
      getAggregatedValue(AGGREGATOR_APPLICATION_STACK);
    if (stack.getStackSize() == 0) {
      long ruleApps = ((LongWritable)
        getAggregatedValue(AGGREGATOR_RULE_APPLICATIONS)).get();
      if (ruleApps == 0) {
        vertex.voteToHalt();
      }
      return;
    }
    int rule = stack.getLastUnit();
    int microstep = stack.getLastMicrostep();
    switch (rule) {
    case RULE_WHEEL:
      matchWheel(vertex, matches, microstep);
      break;
    default:
      throw new RuntimeException("Unknown rule: " + rule);
    }
  }

  /**
   * Match (and apply) the rule "Wheel".
   * This takes 5 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchWheel(
      Vertex<VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<Match> matches, int microstep) throws IOException {

    LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
      " matching rule Wheel in microstep " + microstep);
    for (Match match : matches) {
      LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
        " received (partial) match " + match);
    }
    if (microstep == 0) {
      // Matching node "a":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX_CONTAINER.get();
      if (ok) {
        Match match = new Match().append(vertex.getId());
        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_CONTAINER_VERTICES.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " forward to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      }
    } else if (microstep == 1) {
      // Matching node "b":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        for (Match match : matches) {
          match = match.append(vertex.getId());
          if (!match.isInjective()) {
            continue;
          }
          for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {
            if (edge.getValue().get() ==
              TYPE_VERTEX_RIGHT.get()) {
              LOG.info("Vertex " + vertex.getId() +
                " sending (partial) match " + match +
                " forward to vertex " + edge.getTargetVertexId());
              sendMessage(edge.getTargetVertexId(), match);
            }
          }
        }
      }
    } else if (microstep == 2) {
      // Matching node "c":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        for (Match match : matches) {
          match = match.append(vertex.getId());
          if (!match.isInjective()) {
            continue;
          }
          // Send the message back to matches of node "b":
          for (Match m : matches) {
            VertexId targetId = m.getVertexId(1);
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " back to vertex " + targetId);
            sendMessage(targetId, match);
          }
        }
      }
    } else if (microstep == 3) {
      // Matching node "d":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        Match match = new Match().append(vertex.getId());
        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_RIGHT.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " forward to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      }
      // Keep matches received at node "b":
      for (Match match : matches) {
        VertexId id = match.getVertexId(1);
        if (vertex.getId().equals(id)) {
          LOG.info("Vertex " + id + " in superstep " + getSuperstep() +
            " sending (partial) match " + match + " to myself");
          sendMessage(id, match);
        }
      }
    } else if (microstep == 4) {
      // Joining matches at node "b":
      LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
        " joining matches of rule Wheel");
      for (Match m1 : matches) {
        VertexId id1 = m1.getVertexId(1);
        if (vertex.getId().equals(id1)) {
          for (Match m2 : matches) {
            VertexId id2 = m2.getVertexId(1);
            if (!vertex.getId().equals(id2)) {
              Match joined = m1.append(m2);
              if (!joined.isInjective()) {
                continue;
              }
              applyWheel(vertex, joined);
            }
          }
        }
      }
    } else {
      throw new RuntimeException("Illegal microstep for rule " +
        "Wheel: " + microstep);
    }
  }

  /**
   * Apply the rule "Wheel" to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applyWheel(Vertex<VertexId, ByteWritable,
    ByteWritable> vertex, Match match) throws IOException {
    LOG.info("Vertex " + vertex.getId() +
      " applying rule Wheel with match " + match);
    VertexId cur0 = match.getVertexId(0);
    VertexId cur1 = match.getVertexId(1);
    VertexId cur2 = match.getVertexId(2);
    VertexId cur3 = match.getVertexId(3);
    removeEdgesRequest(cur0, cur1);
    removeEdgesRequest(cur3, cur1);
    removeEdgesRequest(cur1, cur2);
    removeVertexRequest(cur1);
    VertexId src0 = cur0;
    VertexId trg0 = cur2;
    Edge<VertexId, ByteWritable> edge0 =
      EdgeFactory.create(trg0, TYPE_VERTEX_CONTAINER_VERTICES);
    addEdgeRequest(src0, edge0);
    VertexId src1 = cur3;
    VertexId trg1 = cur2;
    Edge<VertexId, ByteWritable> edge1 =
      EdgeFactory.create(trg1, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src1, edge1);
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));
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
    private final Deque<List<Integer>> unitOrders =
      new ArrayDeque<List<Integer>>();

    /*
     * (non-Javadoc)
     * @see org.apache.giraph.master.DefaultMasterCompute#compute()
     */
    @Override
    public void compute() {
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
      ApplicationStack stack;
      if (getSuperstep() == 0) {
        stack = new ApplicationStack();
        stack = stack.append(UNIT_WHEEL_MAIN, 0);
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
    private ApplicationStack nextRuleStep(
      ApplicationStack stack, long ruleApps) {
      while (stack.getStackSize() > 0) {
        int unit = stack.getLastUnit();
        int microstep = stack.getLastMicrostep();
        stack = stack.removeLast();
        switch (unit) {
        case UNIT_WHEEL_MAIN:
          stack = processWheelMain(
            stack, microstep);
          break;
        case RULE_WHEEL:
          stack = processWheel(
            stack, microstep, ruleApps);
          break;
        default:
          throw new RuntimeException("Unknown unit " + unit);
        }
        if (stack.getStackSize() > 0) {
          unit = stack.getLastUnit();
          if (unit == RULE_WHEEL) {
            break;
          }
        }
      }
      return stack;
    }

   /**
     * Process LoopUnit "WheelMain".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @return the new application stack.
     */
    private ApplicationStack processWheelMain(
      ApplicationStack stack, int microstep) {
      if (microstep == 0 || unitSuccesses.pop()) {
        stack = stack.append(UNIT_WHEEL_MAIN, 1);
        stack = stack.append(RULE_WHEEL, 0);
      } else {
        unitSuccesses.push(true);
      }
      return stack;
    }

   /**
     * Process Rule "Wheel".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private ApplicationStack processWheel(
      ApplicationStack stack, int microstep, long ruleApps) {
      if (microstep < 4) {
        stack = stack.append(RULE_WHEEL, microstep + 1);
      } else {
        unitSuccesses.push(ruleApps > 0);
      }
      return stack;
    }

    /*
     * (non-Javadoc)
     * @see org.apache.giraph.master.DefaultMasterCompute#initialize()
     */
    @Override
    public void initialize() throws InstantiationException,
        IllegalAccessException {
      registerAggregator(AGGREGATOR_RULE_APPLICATIONS,
        LongSumAggregator.class);
      registerPersistentAggregator(AGGREGATOR_NODE_GENERATION,
        LongSumAggregator.class);
      registerPersistentAggregator(AGGREGATOR_APPLICATION_STACK,
        ApplicationStackAggregator.class);
    }

  }
}
