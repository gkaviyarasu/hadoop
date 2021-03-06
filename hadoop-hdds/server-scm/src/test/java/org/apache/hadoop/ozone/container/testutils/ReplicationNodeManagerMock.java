/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership.  The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.hadoop.ozone.container.testutils;

import com.google.common.base.Preconditions;
import org.apache.hadoop.hdds.scm.container.placement.metrics.SCMNodeMetric;
import org.apache.hadoop.hdds.scm.container.placement.metrics.SCMNodeStat;
import org.apache.hadoop.hdds.scm.node.CommandQueue;
import org.apache.hadoop.hdds.scm.node.NodeManager;
import org.apache.hadoop.hdds.scm.node.NodePoolManager;
import org.apache.hadoop.hdfs.protocol.UnregisteredNodeException;
import org.apache.hadoop.hdds.protocol.DatanodeDetails;
import org.apache.hadoop.hdds.protocol.proto.HddsProtos;
import org.apache.hadoop.hdds.protocol.proto.HddsProtos.NodeState;
import org.apache.hadoop.hdds.protocol.proto
    .StorageContainerDatanodeProtocolProtos.ReportState;
import org.apache.hadoop.hdds.protocol.proto
    .StorageContainerDatanodeProtocolProtos.SCMNodeReport;
import org.apache.hadoop.hdds.protocol.proto
    .StorageContainerDatanodeProtocolProtos.SCMVersionRequestProto;
import org.apache.hadoop.ozone.protocol.VersionResponse;
import org.apache.hadoop.ozone.protocol.commands.SCMCommand;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A Node Manager to test replication.
 */
public class ReplicationNodeManagerMock implements NodeManager {
  private final Map<DatanodeDetails, NodeState> nodeStateMap;
  private final CommandQueue commandQueue;

  /**
   * A list of Datanodes and current states.
   * @param nodeState A node state map.
   */
  public ReplicationNodeManagerMock(Map<DatanodeDetails, NodeState> nodeState,
                                    CommandQueue commandQueue) {
    Preconditions.checkNotNull(nodeState);
    this.nodeStateMap = nodeState;
    this.commandQueue = commandQueue;
  }

  /**
   * Get the minimum number of nodes to get out of chill mode.
   *
   * @return int
   */
  @Override
  public int getMinimumChillModeNodes() {
    return 0;
  }

  /**
   * Returns a chill mode status string.
   *
   * @return String
   */
  @Override
  public String getChillModeStatus() {
    return null;
  }

  /**
   * Get the number of data nodes that in all states.
   *
   * @return A state to number of nodes that in this state mapping
   */
  @Override
  public Map<String, Integer> getNodeCount() {
    return null;
  }

  /**
   * Removes a data node from the management of this Node Manager.
   *
   * @param node - DataNode.
   * @throws UnregisteredNodeException
   */
  @Override
  public void removeNode(DatanodeDetails node)
      throws UnregisteredNodeException {
    nodeStateMap.remove(node);

  }

  /**
   * Gets all Live Datanodes that is currently communicating with SCM.
   *
   * @param nodestate - State of the node
   * @return List of Datanodes that are Heartbeating SCM.
   */
  @Override
  public List<DatanodeDetails> getNodes(NodeState nodestate) {
    return null;
  }

  /**
   * Returns the Number of Datanodes that are communicating with SCM.
   *
   * @param nodestate - State of the node
   * @return int -- count
   */
  @Override
  public int getNodeCount(NodeState nodestate) {
    return 0;
  }

  /**
   * Get all datanodes known to SCM.
   *
   * @return List of DatanodeDetails known to SCM.
   */
  @Override
  public List<DatanodeDetails> getAllNodes() {
    return null;
  }

  /**
   * Chill mode is the period when node manager waits for a minimum
   * configured number of datanodes to report in. This is called chill mode
   * to indicate the period before node manager gets into action.
   * <p>
   * Forcefully exits the chill mode, even if we have not met the minimum
   * criteria of the nodes reporting in.
   */
  @Override
  public void forceExitChillMode() {

  }

  /**
   * Puts the node manager into manual chill mode.
   */
  @Override
  public void enterChillMode() {

  }

  /**
   * Brings node manager out of manual chill mode.
   */
  @Override
  public void exitChillMode() {

  }

  /**
   * Returns true if node manager is out of chill mode, else false.
   * @return true if out of chill mode, else false
   */
  @Override
  public boolean isOutOfChillMode() {
    return !nodeStateMap.isEmpty();
  }

  /**
   * Returns the aggregated node stats.
   *
   * @return the aggregated node stats.
   */
  @Override
  public SCMNodeStat getStats() {
    return null;
  }

  /**
   * Return a map of node stats.
   *
   * @return a map of individual node stats (live/stale but not dead).
   */
  @Override
  public Map<UUID, SCMNodeStat> getNodeStats() {
    return null;
  }

  /**
   * Return the node stat of the specified datanode.
   *
   * @param dd - datanode details.
   * @return node stat if it is live/stale, null if it is dead or does't exist.
   */
  @Override
  public SCMNodeMetric getNodeStat(DatanodeDetails dd) {
    return null;
  }

  @Override
  public NodePoolManager getNodePoolManager() {
    return Mockito.mock(NodePoolManager.class);
  }

  /**
   * Wait for the heartbeat is processed by NodeManager.
   *
   * @return true if heartbeat has been processed.
   */
  @Override
  public boolean waitForHeartbeatProcessed() {
    return false;
  }

  /**
   * Returns the node state of a specific node.
   *
   * @param dd - DatanodeDetails
   * @return Healthy/Stale/Dead.
   */
  @Override
  public NodeState getNodeState(DatanodeDetails dd) {
    return nodeStateMap.get(dd);
  }

  /**
   * Closes this stream and releases any system resources associated
   * with it. If the stream is already closed then invoking this
   * method has no effect.
   * <p>
   * <p> As noted in {@link AutoCloseable#close()}, cases where the
   * close may fail require careful attention. It is strongly advised
   * to relinquish the underlying resources and to internally
   * <em>mark</em> the {@code Closeable} as closed, prior to throwing
   * the {@code IOException}.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {

  }

  /**
   * When an object implementing interface <code>Runnable</code> is used
   * to create a thread, starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method <code>run</code> is that it may
   * take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {

  }

  /**
   * Gets the version info from SCM.
   *
   * @param versionRequest - version Request.
   * @return - returns SCM version info and other required information needed by
   * datanode.
   */
  @Override
  public VersionResponse getVersion(SCMVersionRequestProto versionRequest) {
    return null;
  }

  /**
   * Register the node if the node finds that it is not registered with any SCM.
   *
   * @param dd DatanodeDetailsProto
   *
   * @return SCMHeartbeatResponseProto
   */
  @Override
  public SCMCommand register(HddsProtos.DatanodeDetailsProto dd) {
    return null;
  }

  /**
   * Send heartbeat to indicate the datanode is alive and doing well.
   *
   * @param dd - Datanode Details.
   * @param nodeReport - node report.
   * @param containerReportState - container report state.
   * @return SCMheartbeat response list
   */
  @Override
  public List<SCMCommand> sendHeartbeat(HddsProtos.DatanodeDetailsProto dd,
      SCMNodeReport nodeReport, ReportState containerReportState) {
    return null;
  }

  /**
   * Clears all nodes from the node Manager.
   */
  public void clearMap() {
    this.nodeStateMap.clear();
  }

  /**
   * Adds a node to the existing Node manager. This is used only for test
   * purposes.
   * @param id DatanodeDetails
   * @param state State you want to put that node to.
   */
  public void addNode(DatanodeDetails id, NodeState state) {
    nodeStateMap.put(id, state);
  }

  @Override
  public void addDatanodeCommand(UUID dnId, SCMCommand command) {
    this.commandQueue.addCommand(dnId, command);
  }

}
