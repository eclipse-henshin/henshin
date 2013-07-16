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
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.master.DefaultMasterCompute;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

/**
 * Generated implementation of the Henshin unit "ForkMain".
 */
@Algorithm(
    name = "ForkMain"
)
public class ForkMain extends
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
   * Unit constant for "ForkMain".
   */
  public static final int UNIT_FORK_MAIN = 0;

  /**
   * Unit constant for "ForkChoice".
   */
  public static final int UNIT_FORK_CHOICE = 1;

  /**
   * Rule constant for "ForkLeft".
   */
  public static final int RULE_FORK_LEFT = 2;

  /**
   * Rule constant for "ForkRight".
   */
  public static final int RULE_FORK_RIGHT = 3;

  /**
   * Rule constant for "ForkConn".
   */
  public static final int RULE_FORK_CONN = 4;

  /**
   * Logging support.
   */
  protected static final Logger LOG = Logger.getLogger(ForkMain.class);

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
    case RULE_FORK_RIGHT:
      matchForkRight(vertex, matches, microstep);
      break;
    case RULE_FORK_LEFT:
      matchForkLeft(vertex, matches, microstep);
      break;
    case RULE_FORK_CONN:
      matchForkConn(vertex, matches, microstep);
      break;
    default:
      throw new RuntimeException("Unknown rule: " + rule);
    }
  }

  /**
   * Match (and apply) the rule "ForkRight".
   * This takes 2 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchForkRight(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches,
      int microstep) throws IOException {

    LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
      " matching rule ForkRight in microstep " + microstep);
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
        // Send a match request to all outgoing edges of type "right":
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_RIGHT.get()) {
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
          applyForkRight(vertex, match);
        }
      } // end if ok

    } else {
      throw new RuntimeException("Illegal microstep for rule " +
        "ForkRight: " + microstep);
    }
  }

  /**
   * Apply the rule "ForkRight" to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applyForkRight(
    Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex,
    HenshinUtil.Match match) throws IOException {

    LOG.info("Vertex " + vertex.getId() +
      " applying rule ForkRight with match " + match);

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);

    // Remove edge "a" -> "b":
    removeEdgesRequest(cur0, cur1);

    // Remove vertex "b":
    removeVertexRequest(cur1);

    // Update the statistics:
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));

  }

  /**
   * Match (and apply) the rule "ForkLeft".
   * This takes 2 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchForkLeft(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches,
      int microstep) throws IOException {

    LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
      " matching rule ForkLeft in microstep " + microstep);
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
          // Apply the rule:
          applyForkLeft(vertex, match);
        }
      } // end if ok

    } else {
      throw new RuntimeException("Illegal microstep for rule " +
        "ForkLeft: " + microstep);
    }
  }

  /**
   * Apply the rule "ForkLeft" to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applyForkLeft(
    Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex,
    HenshinUtil.Match match) throws IOException {

    LOG.info("Vertex " + vertex.getId() +
      " applying rule ForkLeft with match " + match);

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);

    // Remove edge "a" -> "b":
    removeEdgesRequest(cur0, cur1);

    // Remove vertex "b":
    removeVertexRequest(cur1);

    // Update the statistics:
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));

  }

  /**
   * Match (and apply) the rule "ForkConn".
   * This takes 2 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchForkConn(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches,
      int microstep) throws IOException {

    LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
      " matching rule ForkConn in microstep " + microstep);
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
          applyForkConn(vertex, match);
        }
      } // end if ok

    } else {
      throw new RuntimeException("Illegal microstep for rule " +
        "ForkConn: " + microstep);
    }
  }

  /**
   * Apply the rule "ForkConn" to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  protected void applyForkConn(
    Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex,
    HenshinUtil.Match match) throws IOException {

    LOG.info("Vertex " + vertex.getId() +
      " applying rule ForkConn with match " + match);

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);

    // Remove edge "a" -> "b":
    removeEdgesRequest(cur0, cur1);

    // Remove vertex "b":
    removeVertexRequest(cur1);

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
        stack = stack.append(UNIT_FORK_MAIN, 0);
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
        case UNIT_FORK_MAIN:
          stack = processForkMain(
            stack, microstep, ruleApps);
          break;
        case UNIT_FORK_CHOICE:
          stack = processForkChoice(
            stack, microstep, ruleApps);
          break;
        case RULE_FORK_LEFT:
          stack = processForkLeft(
            stack, microstep, ruleApps);
          break;
        case RULE_FORK_RIGHT:
          stack = processForkRight(
            stack, microstep, ruleApps);
          break;
        case RULE_FORK_CONN:
          stack = processForkConn(
            stack, microstep, ruleApps);
          break;
        default:
          throw new RuntimeException("Unknown unit " + unit);
        }
        // If the last unit is a rule, we can stop:
        if (stack.getStackSize() > 0) {
          unit = stack.getLastUnit();
          if (unit == RULE_FORK_RIGHT ||
            unit == RULE_FORK_LEFT ||
            unit == RULE_FORK_CONN) {
            break;
          }
        }
      }
      return stack;
    }

   /**
     * Process LoopUnit "ForkMain".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processForkMain(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep == 0 || unitSuccesses.pop()) {
        stack = stack.append(UNIT_FORK_MAIN, 1);
        stack = stack.append(UNIT_FORK_CHOICE, 0);
      } else {
        unitSuccesses.push(true); // always successful
      }
      return stack;
    }

   /**
     * Process IndependentUnit "ForkChoice".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processForkChoice(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep == 0) {
        // Prepare a random execution order:
        List<Integer> order = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
          order.add(i);
        }
        Collections.shuffle(order);
        independentUnitOrders.push(order);
      }
      if (microstep > 0 && unitSuccesses.pop()) {
        independentUnitOrders.pop();
        unitSuccesses.push(true); // success
      } else if (microstep == 3) {
        independentUnitOrders.pop();
        unitSuccesses.push(false); // no success
      } else {
        // Choose and execute the next subunit:
        int next = independentUnitOrders.peek().get(microstep);
        switch (next) {
        case 0:
          stack = stack.append(UNIT_FORK_CHOICE, microstep + 1);
          stack = stack.append(RULE_FORK_LEFT, 0);
          break;
        case 1:
          stack = stack.append(UNIT_FORK_CHOICE, microstep + 1);
          stack = stack.append(RULE_FORK_RIGHT, 0);
          break;
        case 2:
          stack = stack.append(UNIT_FORK_CHOICE, microstep + 1);
          stack = stack.append(RULE_FORK_CONN, 0);
          break;
        default:
          break;
        }
      }
      return stack;
    }

   /**
     * Process Rule "ForkLeft".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processForkLeft(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep < 1) {
        stack = stack.append(RULE_FORK_LEFT, microstep + 1);
      } else {
        unitSuccesses.push(ruleApps > 0);
      }
      return stack;
    }

   /**
     * Process Rule "ForkRight".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processForkRight(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep < 1) {
        stack = stack.append(RULE_FORK_RIGHT, microstep + 1);
      } else {
        unitSuccesses.push(ruleApps > 0);
      }
      return stack;
    }

   /**
     * Process Rule "ForkConn".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private HenshinUtil.ApplicationStack processForkConn(
      HenshinUtil.ApplicationStack stack, int microstep, long ruleApps) {

      // Update the application stack:
      if (microstep < 1) {
        stack = stack.append(RULE_FORK_CONN, microstep + 1);
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
