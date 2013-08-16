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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.giraph.aggregators.LongSumAggregator;
import org.apache.giraph.edge.Edge;
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
 * Generated implementation of the Henshin unit "TwoTimesThree".
 */
@Algorithm(
    name = "TwoTimesThree"
)
public class TwoTimesThree extends
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
   * Rule constant for "TwoTimesThree".
   */
  public static final int RULE_TWO_TIMES_THREE = 0;

  /**
   * Logging support.
   */
  protected static final Logger LOG = Logger.getLogger(TwoTimesThree.class);

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
    case RULE_TWO_TIMES_THREE:
      matchTwoTimesThree(vertex, matches, microstep);
      break;
    default:
      throw new RuntimeException("Unknown rule: " + rule);
    }
  }

  /**
   * Match (and apply) the rule "TwoTimesThree".
   * This takes 8 microsteps.
   * @param vertex The current vertex.
   * @param matches The current matches.
   * @param microstep Current microstep.
   */
  protected void matchTwoTimesThree(
      Vertex<VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<Match> matches, int microstep) throws IOException {

    LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
      " matching rule TwoTimesThree in microstep " + microstep);
    for (Match match : matches) {
      LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
        " received (partial) match " + match);
    }
    Set<Match> appliedMatches = new HashSet<Match>();
    if (microstep == 0) {
      // Matching node "a":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        Match match = new Match().append(vertex.getId());
        // Send the match along all "left"-edges:
        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_LEFT.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " forward to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      }
    } else if (microstep == 1) {
      // Matching node "x":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        for (Match match : matches) {
          match = match.append(vertex.getId());
          if (!match.isInjective()) {
            continue;
          }
          // Send the message back to matches of node "a":
          for (Match m : matches) {
            VertexId recipient = m.getVertexId(0);
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " back to vertex " + recipient);
            sendMessage(recipient, match);
          }
        }
      }
    } else if (microstep == 2) {
      for (Match match : matches) {
        if (!match.isInjective()) {
          continue;
        }
        // Send the match along all "conn"-edges:
        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_CONN.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " forward to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      }
    } else if (microstep == 3) {
      // Matching node "y":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        for (Match match : matches) {
          match = match.append(vertex.getId());
          if (!match.isInjective()) {
            continue;
          }
          // Send the message back to matches of node "a":
          for (Match m : matches) {
            VertexId recipient = m.getVertexId(0);
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " back to vertex " + recipient);
            sendMessage(recipient, match);
          }
        }
      }
    } else if (microstep == 4) {
      for (Match match : matches) {
        if (!match.isInjective()) {
          continue;
        }
        // Send the match along all "right"-edges:
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
    } else if (microstep == 5) {
      // Matching node "z":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        for (Match match : matches) {
          match = match.append(vertex.getId());
          if (!match.isInjective()) {
            continue;
          }
          // Send the message back to matches of node "x":
          for (Match m : matches) {
            VertexId recipient = m.getVertexId(1);
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " back to vertex " + recipient);
            sendMessage(recipient, match);
          }
        }
      }
    } else if (microstep == 6) {
      // Matching node "b":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        Match match = new Match().append(vertex.getId());
          // Node "b": check for edge to match of "x" of type "left":
          VertexId targetId = match.getVertexId(1);
          for (Edge<VertexId, ByteWritable> edge :
            vertex.getEdges()) {
            if (edge.getValue().get() ==
              TYPE_VERTEX_LEFT.get() &&
              edge.getTargetVertexId().equals(targetId)) {
            }
          }
      }
      // Keep matches received at node "x":
      for (Match match : matches) {
        VertexId id = match.getVertexId(1);
        if (vertex.getId().equals(id)) {
          LOG.info("Vertex " + id + " in superstep " + getSuperstep() +
            " sending (partial) match " + match + " to myself");
          sendMessage(id, match);
        }
      }
    } else if (microstep == 7) {
      // Joining matches at node "x":
      LOG.info("Vertex " + vertex.getId() + " in superstep " + getSuperstep() +
        " joining matches of rule TwoTimesThree");
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
              applyTwoTimesThree(vertex, joined, appliedMatches);
            }
          }
        }
      }
    } else {
      throw new RuntimeException("Illegal microstep for rule " +
        "TwoTimesThree: " + microstep);
    }
  }

  /**
   * Apply the rule "TwoTimesThree" to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @param appliedMatches Set of already applied matches.
   * @return true if the rule was applied.
   * @throws IOException On I/O errors.
   */
  protected boolean applyTwoTimesThree(Vertex<VertexId, ByteWritable,
    ByteWritable> vertex, Match match, Set<Match> appliedMatches)
    throws IOException {
    VertexId cur0 = match.getVertexId(0);
    VertexId cur1 = match.getVertexId(1);
    VertexId cur2 = match.getVertexId(2);
    VertexId cur3 = match.getVertexId(3);
    VertexId cur4 = match.getVertexId(4);
    if (!appliedMatches.add(match)) {
      return false;
    }
    LOG.info("Vertex " + vertex.getId() +
      " applying rule TwoTimesThree with match " + match);
    removeEdgesRequest(cur0, cur1);
    removeEdgesRequest(cur0, cur2);
    removeEdgesRequest(cur0, cur3);
    removeEdgesRequest(cur4, cur1);
    removeEdgesRequest(cur4, cur2);
    removeEdgesRequest(cur4, cur3);
    removeVertexRequest(cur0);
    removeVertexRequest(cur4);
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));
    return true;
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
        stack = stack.append(RULE_TWO_TIMES_THREE, 0);
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
        case RULE_TWO_TIMES_THREE:
          stack = processTwoTimesThree(
            stack, microstep, ruleApps);
          break;
        default:
          throw new RuntimeException("Unknown unit " + unit);
        }
        if (stack.getStackSize() > 0) {
          unit = stack.getLastUnit();
          if (unit == RULE_TWO_TIMES_THREE) {
            break;
          }
        }
      }
      return stack;
    }

   /**
     * Process Rule "TwoTimesThree".
     * @param stack Current application stack.
     * @param microstep Current microstep.
     * @param ruleApps Number of rule applications in last superstep.
     * @return the new application stack.
     */
    private ApplicationStack processTwoTimesThree(
      ApplicationStack stack, int microstep, long ruleApps) {
      if (microstep < 7) {
        stack = stack.append(RULE_TWO_TIMES_THREE, microstep + 1);
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
