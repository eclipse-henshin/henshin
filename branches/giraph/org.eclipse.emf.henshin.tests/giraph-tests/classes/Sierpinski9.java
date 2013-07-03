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

import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.ByteWritable;

/**
 * Generated implementation of the Henshin rule "Sierpinski".
 */
@Algorithm(
    name = "Sierpinski"
)
public class Sierpinski9 extends
  BasicComputation<HenshinUtil.VertexId, ByteWritable,
  ByteWritable, HenshinUtil.Match> {

  /**
   * Default number of applications of this rule.
   */
  public static final int DEFAULT_APPLICATION_COUNT = 9;

  /**
   * Type constant for "Vertex".
   */
  public static final ByteWritable SIERPINSKI_VERTEX
    = new ByteWritable((byte) 0);

  /**
   * Type constant for "left".
   */
  public static final ByteWritable SIERPINSKI_VERTEX_LEFT
    = new ByteWritable((byte) 1);

  /**
   * Type constant for "conn".
   */
  public static final ByteWritable SIERPINSKI_VERTEX_CONN
    = new ByteWritable((byte) 2);

  /**
   * Type constant for "right".
   */
  public static final ByteWritable SIERPINSKI_VERTEX_RIGHT
    = new ByteWritable((byte) 3);

  /**
   * Type constant for "VertexContainer".
   */
  public static final ByteWritable SIERPINSKI_VERTEX_CONTAINER
    = new ByteWritable((byte) 4);

  /**
   * Type constant for "vertices".
   */
  public static final ByteWritable SIERPINSKI_VERTEX_CONTAINER_VERTICES
    = new ByteWritable((byte) 5);

  /**
   * Number of applications of this rule.
   */
  private int applicationCount = DEFAULT_APPLICATION_COUNT;

  /*
   * (non-Javadoc)
   * @see org.apache.giraph.graph.Computation#compute(
   *        org.apache.giraph.graph.Vertex, java.lang.Iterable)
   */
  @Override
  public void compute(
      Vertex<HenshinUtil.VertexId, ByteWritable, ByteWritable> vertex,
      Iterable<HenshinUtil.Match> matches) throws IOException {

    // Get the current superstep:
    long superstep = getSuperstep();

    // Check if we can stop:
    if (superstep >= applicationCount * 4) {
      vertex.voteToHalt();
      return;
    }

    if (superstep % 4 == 0) {

      // Matching node 0. Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX.get();
      if (ok) {
        // Create a new partial match:
        HenshinUtil.Match match =
          new HenshinUtil.Match().extend(vertex.getId());
        // Send a match request to all outgoing edges of type "left":
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            SIERPINSKI_VERTEX_LEFT.get()) {
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      } // end if ok

      // In the last iteration the vertex can be made inactive:
      if (superstep / 4 == applicationCount - 1) {
        vertex.voteToHalt();
      }

    } else if (superstep % 4 == 1) {

      // Matching node 1. Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.extend(vertex.getId());
          // Send a match request to all outgoing edges of type "conn":
          for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
            vertex.getEdges()) {
            if (edge.getValue().get() ==
              SIERPINSKI_VERTEX_CONN.get()) {
              sendMessage(edge.getTargetVertexId(), match);
            }
          }
        }
      } // end if ok

      // In the last iteration the vertex can be made inactive:
      if (superstep / 4 == applicationCount - 1) {
        vertex.voteToHalt();
      }

    } else if (superstep % 4 == 2) {

      // Matching node 2. Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.extend(vertex.getId());
          // Send the message back to matches of node 0:
          for (HenshinUtil.Match m : matches) {
            HenshinUtil.VertexId targetVertexId =
              m.getVertexId(0);
            sendMessage(targetVertexId, match);
          }
        }
      } // end if ok

      // In the last iteration the vertex can be made inactive:
      if (superstep / 4 == applicationCount - 1) {
        vertex.voteToHalt();
      }

    } else if (superstep % 4 == 3) {

      // Node 0: check for edge to match of 2 of type "right":
      for (HenshinUtil.Match match : matches) {
        HenshinUtil.VertexId targetId = match.getVertexId(2);
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            SIERPINSKI_VERTEX_RIGHT.get() &&
            edge.getTargetVertexId().equals(targetId)) {
            // Apply the rule:
            applyRule(vertex, match);
          }
        }
      }


    }

  }

  /**
   * Apply the rule to a given match.
   * @param vertex The base vertex.
   * @param match The match object.
   * @throws IOException On I/O errors.
   */
  private void applyRule(Vertex<HenshinUtil.VertexId, ByteWritable,
    ByteWritable> vertex, HenshinUtil.Match match) throws IOException {

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);
    HenshinUtil.VertexId cur2 = match.getVertexId(2);

    // Remove edge 0 -> 1:
    removeEdgesRequest(cur0, cur1);

    // Remove edge 0 -> 2:
    removeEdgesRequest(cur0, cur2);

    // Remove edge 1 -> 2:
    removeEdgesRequest(cur1, cur2);

    // Create vertex 3:
    HenshinUtil.VertexId new0 =
      deriveVertexId(vertex.getId(), (byte) 0);
    addVertexRequest(new0, SIERPINSKI_VERTEX);

    // Create vertex 4:
    HenshinUtil.VertexId new1 =
      deriveVertexId(vertex.getId(), (byte) 1);
    addVertexRequest(new1, SIERPINSKI_VERTEX);

    // Create vertex 5:
    HenshinUtil.VertexId new2 =
      deriveVertexId(vertex.getId(), (byte) 2);
    addVertexRequest(new2, SIERPINSKI_VERTEX);

    // Create edge 0 -> 3:
    HenshinUtil.VertexId src0 = cur0;
    HenshinUtil.VertexId trg0 = new0;
    Edge<HenshinUtil.VertexId, ByteWritable> edge0 =
      EdgeFactory.create(trg0, SIERPINSKI_VERTEX_LEFT);
    addEdgeRequest(src0, edge0);

    // Create edge 0 -> 4:
    HenshinUtil.VertexId src1 = cur0;
    HenshinUtil.VertexId trg1 = new1;
    Edge<HenshinUtil.VertexId, ByteWritable> edge1 =
      EdgeFactory.create(trg1, SIERPINSKI_VERTEX_RIGHT);
    addEdgeRequest(src1, edge1);

    // Create edge 1 -> 5:
    HenshinUtil.VertexId src2 = cur1;
    HenshinUtil.VertexId trg2 = new2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge2 =
      EdgeFactory.create(trg2, SIERPINSKI_VERTEX_CONN);
    addEdgeRequest(src2, edge2);

    // Create edge 3 -> 1:
    HenshinUtil.VertexId src3 = new0;
    HenshinUtil.VertexId trg3 = cur1;
    Edge<HenshinUtil.VertexId, ByteWritable> edge3 =
      EdgeFactory.create(trg3, SIERPINSKI_VERTEX_LEFT);
    addEdgeRequest(src3, edge3);

    // Create edge 3 -> 4:
    HenshinUtil.VertexId src4 = new0;
    HenshinUtil.VertexId trg4 = new1;
    Edge<HenshinUtil.VertexId, ByteWritable> edge4 =
      EdgeFactory.create(trg4, SIERPINSKI_VERTEX_CONN);
    addEdgeRequest(src4, edge4);

    // Create edge 3 -> 5:
    HenshinUtil.VertexId src5 = new0;
    HenshinUtil.VertexId trg5 = new2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge5 =
      EdgeFactory.create(trg5, SIERPINSKI_VERTEX_RIGHT);
    addEdgeRequest(src5, edge5);

    // Create edge 4 -> 5:
    HenshinUtil.VertexId src6 = new1;
    HenshinUtil.VertexId trg6 = new2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge6 =
      EdgeFactory.create(trg6, SIERPINSKI_VERTEX_LEFT);
    addEdgeRequest(src6, edge6);

    // Create edge 4 -> 2:
    HenshinUtil.VertexId src7 = new1;
    HenshinUtil.VertexId trg7 = cur2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge7 =
      EdgeFactory.create(trg7, SIERPINSKI_VERTEX_RIGHT);
    addEdgeRequest(src7, edge7);

    // Create edge 5 -> 2:
    HenshinUtil.VertexId src8 = new2;
    HenshinUtil.VertexId trg8 = cur2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge8 =
      EdgeFactory.create(trg8, SIERPINSKI_VERTEX_CONN);
    addEdgeRequest(src8, edge8);

  }

  /**
   * Derive a new vertex Id from an exiting one.
   * @param baseId The base vertex Id.
   * @param vertexIndex The relative index of the new vertex.
   * @return The derived vertex Id.
   */
  private HenshinUtil.VertexId deriveVertexId(
    HenshinUtil.VertexId baseId, int vertexIndex) {
    int appCount = applicationCount;
    int bitsNeededForApp = 0;
    while (appCount > 0) {
      appCount = appCount / 2;
      bitsNeededForApp++;
    }
    long code = (getSuperstep() + 1) / 4;
    if (bitsNeededForApp <= 6) {
      code = ((code << 2)) | vertexIndex;
      return baseId.extend((byte) code);
    } else {
      return baseId.extend((byte) code).extend((byte) vertexIndex);
    }
  }

  /**
   * Get the number of application to be executed for this rule.
   * @return the number of rule applications.
   */
  public int getApplicationCount() {
    return applicationCount;
  }

  /**
   * Set the number of application to be executed for this rule.
   * @param applicationCount The new number of rule applications.
   */
  public void setApplicationCount(int applicationCount) {
    this.applicationCount = applicationCount;
  }

}
