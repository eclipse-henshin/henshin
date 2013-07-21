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
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.master.DefaultMasterCompute;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.LongWritable;
import static org.apache.giraph.examples.HenshinUtil
  .ApplicationStack;
import static org.apache.giraph.examples.HenshinUtil
  .ApplicationStackAggregator;
import static org.apache.giraph.examples.HenshinUtil
  .Match;
import static org.apache.giraph.examples.HenshinUtil
  .VertexId;

/**
 * Generated implementation of the Henshin unit "SierpinskiMain".
 */
@Algorithm(
    name = "SierpinskiMain"
)
public class SierpinskiMain6 extends
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
   * Unit constant for "SierpinskiMain".
   */
  public static final int UNIT_SIERPINSKI_MAIN = 0;

  /**
   * Rule constant for "Sierpinski".
   */
  public static final int RULE_SIERPINSKI = 1;

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
      Vertex<VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<Match> matches, int microstep) throws IOException {

    Set<Match> appliedMatches = new HashSet<Match>();
    if (microstep == 0) {
      // Matching node "a":
      boolean ok = vertex.getValue().get() == TYPE_VERTEX.get();
      if (ok) {
        Match match = new Match().append(vertex.getId());
        for (Edge<VertexId, ByteWritable> edge : vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_LEFT.get()) {
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
              TYPE_VERTEX_CONN.get()) {
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
          // Send the message back to matches of node "a":
          for (Match m : matches) {
            VertexId recipient = m.getVertexId(0);
            sendMessage(recipient, match);
          }
        }
      }
    } else if (microstep == 3) {
      for (Match match : matches) {
        if (!match.isInjective()) {
          continue;
        }
        // Node "a": check for edge to match of "c" of type "right":
        VertexId targetId = match.getVertexId(2);
        for (Edge<VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            TYPE_VERTEX_RIGHT.get() &&
            edge.getTargetVertexId().equals(targetId)) {
            applySierpinski(vertex, match, appliedMatches);
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
   * @param appliedMatches Set of already applied matches.
   * @return true if the rule was applied.
   * @throws IOException On I/O errors.
   */
  protected boolean applySierpinski(Vertex<VertexId, ByteWritable,
    ByteWritable> vertex, Match match, Set<Match> appliedMatches)
    throws IOException {
    VertexId cur0 = match.getVertexId(0);
    VertexId cur1 = match.getVertexId(1);
    VertexId cur2 = match.getVertexId(2);
    if (!appliedMatches.add(match)) {
      return false;
    }
    removeEdgesRequest(cur0, cur1);
    removeEdgesRequest(cur0, cur2);
    removeEdgesRequest(cur1, cur2);
    VertexId new0 =
      deriveVertexId(vertex.getId(), (byte) 0);
    addVertexRequest(new0, TYPE_VERTEX);
    VertexId new1 =
      deriveVertexId(vertex.getId(), (byte) 1);
    addVertexRequest(new1, TYPE_VERTEX);
    VertexId new2 =
      deriveVertexId(vertex.getId(), (byte) 2);
    addVertexRequest(new2, TYPE_VERTEX);
    VertexId src0 = cur0;
    VertexId trg0 = new0;
    Edge<VertexId, ByteWritable> edge0 =
      EdgeFactory.create(trg0, TYPE_VERTEX_LEFT);
    addEdgeRequest(src0, edge0);
    VertexId src1 = cur0;
    VertexId trg1 = new1;
    Edge<VertexId, ByteWritable> edge1 =
      EdgeFactory.create(trg1, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src1, edge1);
    VertexId src2 = cur1;
    VertexId trg2 = new2;
    Edge<VertexId, ByteWritable> edge2 =
      EdgeFactory.create(trg2, TYPE_VERTEX_CONN);
    addEdgeRequest(src2, edge2);
    VertexId src3 = new0;
    VertexId trg3 = cur1;
    Edge<VertexId, ByteWritable> edge3 =
      EdgeFactory.create(trg3, TYPE_VERTEX_LEFT);
    addEdgeRequest(src3, edge3);
    VertexId src4 = new0;
    VertexId trg4 = new1;
    Edge<VertexId, ByteWritable> edge4 =
      EdgeFactory.create(trg4, TYPE_VERTEX_CONN);
    addEdgeRequest(src4, edge4);
    VertexId src5 = new0;
    VertexId trg5 = new2;
    Edge<VertexId, ByteWritable> edge5 =
      EdgeFactory.create(trg5, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src5, edge5);
    VertexId src6 = new1;
    VertexId trg6 = new2;
    Edge<VertexId, ByteWritable> edge6 =
      EdgeFactory.create(trg6, TYPE_VERTEX_LEFT);
    addEdgeRequest(src6, edge6);
    VertexId src7 = new1;
    VertexId trg7 = cur2;
    Edge<VertexId, ByteWritable> edge7 =
      EdgeFactory.create(trg7, TYPE_VERTEX_RIGHT);
    addEdgeRequest(src7, edge7);
    VertexId src8 = new2;
    VertexId trg8 = cur2;
    Edge<VertexId, ByteWritable> edge8 =
      EdgeFactory.create(trg8, TYPE_VERTEX_CONN);
    addEdgeRequest(src8, edge8);
    aggregate(AGGREGATOR_RULE_APPLICATIONS, new LongWritable(1));
    return true;
  }

  /**
   * Derive a new vertex Id from an exiting one.
   * @param baseId The base vertex Id.
   * @param vertexIndex The relative index of the new vertex.
   * @return The derived vertex Id.
   */
  private VertexId deriveVertexId(VertexId baseId, int vertexIndex) {
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
      if (ruleApps > 0) {
        long nodeGen = ((LongWritable)
          getAggregatedValue(AGGREGATOR_NODE_GENERATION)).get();
        setAggregatedValue(AGGREGATOR_NODE_GENERATION,
          new LongWritable(nodeGen + 1));
      }
      ApplicationStack stack;
      if (getSuperstep() == 0) {
        stack = new ApplicationStack();
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
    private ApplicationStack nextRuleStep(
      ApplicationStack stack, long ruleApps) {
      while (stack.getStackSize() > 0) {
        int unit = stack.getLastUnit();
        int microstep = stack.getLastMicrostep();
        stack = stack.removeLast();
        switch (unit) {
        case UNIT_SIERPINSKI_MAIN:
          stack = processSierpinskiMain(
            stack, microstep);
          break;
        case RULE_SIERPINSKI:
          stack = processSierpinski(
            stack, microstep, ruleApps);
          break;
        default:
          throw new RuntimeException("Unknown unit " + unit);
        }
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
     * @return the new application stack.
     */
    private ApplicationStack processSierpinskiMain(
      ApplicationStack stack, int microstep) {
      if (microstep > 0 && !unitSuccesses.pop()) {
        unitSuccesses.push(false);
      } else if (microstep == 6) {
        unitSuccesses.push(true);
      } else if (microstep < 6) {
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
    private ApplicationStack processSierpinski(
      ApplicationStack stack, int microstep, long ruleApps) {
      if (microstep < 3) {
        stack = stack.append(RULE_SIERPINSKI, microstep + 1);
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
