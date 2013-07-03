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
import org.apache.log4j.Logger;

/**
 * Generated implementation of the Henshin rule "Wheel".
 */
@Algorithm(
    name = "Wheel"
)
public class Wheel10 extends
  BasicComputation<HenshinUtil.VertexId, ByteWritable,
  ByteWritable, HenshinUtil.Match> {

  /**
   * Default number of applications of this rule.
   */
  public static final int DEFAULT_APPLICATION_COUNT = 10;

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
   * Logging support.
   */
  private static final Logger LOG = Logger.getLogger(Wheel10.class);

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
    LOG.info("Vertex " + vertex.getId() + " executing superstep " + superstep);

    // Check if we can stop:
    if (superstep >= applicationCount * 4) {
      vertex.voteToHalt();
      return;
    }

    // Log received (partial) matches:
    for (HenshinUtil.Match match : matches) {
      LOG.info("Vertex " + vertex.getId() +
        " received (partial) match " + match);
    }
    if (superstep % 4 == 0) {

      // Matching node "a". Type must be "VertexContainer":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX_CONTAINER.get();
      if (ok) {
        // Create a new partial match:
        HenshinUtil.Match match =
          new HenshinUtil.Match().extend(vertex.getId());
        // Send a match request to all outgoing edges of type "vertices":
        for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
          vertex.getEdges()) {
          if (edge.getValue().get() ==
            SIERPINSKI_VERTEX_CONTAINER_VERTICES.get()) {
            LOG.info("Vertex " + vertex.getId() +
              " sending (partial) match " + match +
              " to vertex " + edge.getTargetVertexId());
            sendMessage(edge.getTargetVertexId(), match);
          }
        }
      } // end if ok

      // In the last iteration the vertex can be made inactive:
      if (superstep / 4 == applicationCount - 1) {
        vertex.voteToHalt();
      }

    } else if (superstep % 4 == 1) {

      // Matching node "b". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.extend(vertex.getId());
          // Send a match request to all outgoing edges of type "right":
          for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
            vertex.getEdges()) {
            if (edge.getValue().get() ==
              SIERPINSKI_VERTEX_RIGHT.get()) {
              LOG.info("Vertex " + vertex.getId() +
                " sending (partial) match " + match +
                " to vertex " + edge.getTargetVertexId());
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

      // Matching node "c". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.extend(vertex.getId());
        }
      } // end if ok

      // In the last iteration the vertex can be made inactive:
      if (superstep / 4 == applicationCount - 1) {
        vertex.voteToHalt();
      }

    } else if (superstep % 4 == 3) {

      // Matching node "d". Type must be "Vertex":
      boolean ok = vertex.getValue().get() ==
        SIERPINSKI_VERTEX.get();
      if (ok) {
        // Extend all partial matches:
        for (HenshinUtil.Match match : matches) {
          match = match.extend(vertex.getId());
          // Send a match request to all outgoing edges of type "right":
          for (Edge<HenshinUtil.VertexId, ByteWritable> edge :
            vertex.getEdges()) {
            if (edge.getValue().get() ==
              SIERPINSKI_VERTEX_RIGHT.get()) {
              LOG.info("Vertex " + vertex.getId() +
                " sending (partial) match " + match +
                " to vertex " + edge.getTargetVertexId());
              sendMessage(edge.getTargetVertexId(), match);
            }
          }
        }
      } // end if ok

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

    LOG.info("Vertex " + vertex.getId() +
      " applying rule with match " + match);

    HenshinUtil.VertexId cur0 = match.getVertexId(0);
    HenshinUtil.VertexId cur1 = match.getVertexId(1);
    HenshinUtil.VertexId cur2 = match.getVertexId(2);
    HenshinUtil.VertexId cur3 = match.getVertexId(3);

    // Remove edge "a" -> "b":
    removeEdgesRequest(cur0, cur1);

    // Remove edge "d" -> "b":
    removeEdgesRequest(cur3, cur1);

    // Remove edge "b" -> "c":
    removeEdgesRequest(cur1, cur2);

    // Remove vertex "b":
    removeVertexRequest(cur1);

    // Create edge "a" -> "c":
    HenshinUtil.VertexId src0 = cur0;
    HenshinUtil.VertexId trg0 = cur2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge0 =
      EdgeFactory.create(trg0, SIERPINSKI_VERTEX_CONTAINER_VERTICES);
    addEdgeRequest(src0, edge0);

    // Create edge "d" -> "c":
    HenshinUtil.VertexId src1 = cur3;
    HenshinUtil.VertexId trg1 = cur2;
    Edge<HenshinUtil.VertexId, ByteWritable> edge1 =
      EdgeFactory.create(trg1, SIERPINSKI_VERTEX_RIGHT);
    addEdgeRequest(src1, edge1);

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
    if (bitsNeededForApp <= 8) {
      code = ((code << 0)) | vertexIndex;
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
