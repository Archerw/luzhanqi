package org.luzhanqi.client;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Map;

import org.luzhanqi.client.GameApi.Operation;
import org.luzhanqi.client.GameApi.Set;
import org.luzhanqi.client.GameApi.SetVisibility;
import org.luzhanqi.client.GameApi.VerifyMove;
import org.luzhanqi.client.GameApi.VerifyMoveDone;
import org.luzhanqi.client.Piece.PieceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@RunWith(JUnit4.class)
public class LuzhanqiLogicTest {
  
  private void assertMoveOk(VerifyMove verifyMove) {
    VerifyMoveDone verifyDone = new LuzhanqiLogic().verify(verifyMove);
    assertEquals(new VerifyMoveDone(), verifyDone);
  }

  private void assertHacker(VerifyMove verifyMove) {
    VerifyMoveDone verifyDone = new LuzhanqiLogic().verify(verifyMove);
    assertEquals(new VerifyMoveDone(verifyMove.getLastMovePlayerId(), "Hacker found"), verifyDone);
  }
  
  private final int wId = 41;
  private final int bId = 42;
  private final String playerId = "playerId";
  private final String TURN = "turn"; // turn of which player (either W, B, S)
  private static final String W = "W"; // White hand
  private static final String B = "B"; // Black hand
  private static final String S = "S"; // Start arranging pieces B and W
  private static final String D = "D"; // Discard pile
  private static final String READY = "ready"; // after arrange pieces set ready
  //private static final String MOVE = "move"; // move from SLx to SLy
  //private static final String BEAT = "beat"; // beat pieces, maybe both
  private static final String BOARD = "board"; 
  private final Map<String, Object> wInfo = ImmutableMap.<String, Object>of(playerId, wId);
  private final Map<String, Object> bInfo = ImmutableMap.<String, Object>of(playerId, bId);
  private final List<Map<String, Object>> playersInfo = ImmutableList.of(wInfo, bInfo);
  private final Map<String, Object> emptyState = ImmutableMap.<String, Object>of();
  private final Map<String, Object> nonEmptyState = ImmutableMap.<String, Object>of("k", "v");
  
  private final Map<String, Object> initialState = ImmutableMap.<String, Object>of(
      TURN, S,
      BOARD,ImmutableList.of(
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1),
      W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24),
      B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49),     
      D, ImmutableList.of());
  
  private final Map<String, Object> afterFirstDeployW = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          0,24,21,1,2,
          3,4,22,5,23,
          6,-1,8,-1,10,
          11,12,-1,14,15,
          7,-1,13,-1,9,
          16,17,18,19,20,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1),
      W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24),
      B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49),     
      D, ImmutableList.of());

  private final Map<String, Object> beforeSetVAllB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          25,26,27,28,29,
          30,-1,31,-1,32,
          33,34,-1,35,36,
          37,-1,38,-1,39,
          40,47,46,44,43,
          45,49,48,42,41),
      W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24),
      B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49),     
      D, ImmutableList.of());
  
  private final Map<String, Object> setVAllB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          0,24,21,1,2,
          3,4,22,5,23,
          6,-1,8,-1,10,
          11,12,-1,14,15,
          7,-1,13,-1,9,
          16,17,18,19,20,
          25,26,27,28,29,
          30,-1,31,-1,32,
          33,34,-1,35,36,
          37,-1,38,-1,39,
          40,47,46,44,43,
          45,49,48,42,41),
      W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24),
      B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49),     
      D, ImmutableList.of()); 
  
  private final Map<String, Object> illSetVNoBB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          0,24,21,1,2,
          3,4,22,5,23,
          6,-1,8,-1,10,
          11,12,-1,14,15,
          7,-1,13,-1,9,
          16,17,18,19,20,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1),
      W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24),
      B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49),     
      D, ImmutableList.of());
  
  private final Map<String, Object> illWlessB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          0,24,21,1,2,
          3,4,22,5,23,
          6,-1,8,-1,10,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          25,26,27,28,29,
          30,-1,31,-1,32,
          33,34,-1,35,36,
          37,-1,38,-1,39,
          40,47,46,44,43,
          45,49,48,42,41),
      W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24),
      B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49),     
      D, ImmutableList.of()); 
  
  private final Map<String, Object> oldStateB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46)); 
  
  private final Map<String, Object> moveB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,33,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> moveOnRailB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          33,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> moveOnRailEB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,42,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,-1,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> beatB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,41,-1,-1,
          -1,-1,8,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,22,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illTurnB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illVanishB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,-1,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illDoubleMoveB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,-1,-1,-1,42,
          -1,47,-1,-1,-1,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illNoPathB = ImmutableMap.<String, Object>of(
      TURN, B,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,33,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illRailTurnB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,33,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illRailToOutB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,33,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illFlagMoveB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          49,-1,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illLandmineMoveB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          47,-1,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illMoveThruRailB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          42,47,-1,-1,-1,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illReOrderBeatB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,41,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final Map<String, Object> illLandmineEnBeatB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,8,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,24),
      B, ImmutableList.of(33,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,22,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,41,43,44,45,46));
  
  private final Map<String, Object> illMoveWPieceB = ImmutableMap.<String, Object>of(
      TURN, W,
      BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,8,-1,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1),
      W, ImmutableList.of(8,21,22,24),
      B, ImmutableList.of(33,41,42,47,48,49),     
      D, ImmutableList.of(
          1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
          25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46));
  
  private final List<Operation> initialMoveB = ImmutableList.<Operation>of(
      // Black set turn to S, deploy pieces
      new Set(TURN,S),
      new Set(BOARD,ImmutableList.of(
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1)),
      new Set(W, ImmutableList.of(
              1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24)),
      new Set(B, ImmutableList.of(
              25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49)),     
      new Set(D, ImmutableList.of()));
  
  private final List<Operation> illInitialMoveWithExtraMovesB = ImmutableList.<Operation>of(
      // Black set turn to S, deploy pieces
      new Set(TURN,S),
      new Set(BOARD,ImmutableList.of(
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1)),
      new Set(W, ImmutableList.of(
              1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24)),
      new Set(B, ImmutableList.of(
              25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49)),     
      new Set(D, ImmutableList.of()),
      new Set(S,READY));
  
  private final List<Operation> deployB = ImmutableList.<Operation>of(
      new Set(TURN, B),
      new Set(BOARD,ImmutableList.of(
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          25,26,27,28,29,
          30,-1,31,-1,32,
          33,34,-1,35,36,
          37,-1,38,-1,39,
          40,47,46,44,43,
          45,49,48,42,41)),
      new Set(W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24)),
      new Set(B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49)),     
      new Set(D, ImmutableList.of()));
  
  private final List<Operation> setVB = ImmutableList.<Operation>of(
      new Set(TURN, B),
      new Set(BOARD,ImmutableList.of(
          0,24,21,1,2,
          3,4,22,5,23,
          6,-1,8,-1,10,
          11,12,-1,14,15,
          7,-1,13,-1,9,
          16,17,18,19,20,
          25,26,27,28,29,
          30,-1,31,-1,32,
          33,34,-1,35,36,
          37,-1,38,-1,39,
          40,47,46,44,43,
          45,49,48,42,41)),
      new Set(W, ImmutableList.of(
          1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24)),
      new Set(B, ImmutableList.of(
          25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49)),     
      new Set(D, ImmutableList.of()),
      new SetVisibility(W),
      new SetVisibility(B));

  private final List<Operation> normalMoveB = ImmutableList.<Operation>of(
      new Set(TURN, W),
      new Set(BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1)),
      new Set(W, ImmutableList.of(8,21,22,24)),
      new Set(B, ImmutableList.of(33,41,42,47,48,49)),     
      new Set(D, ImmutableList.of(
        1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
        25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46)));
  
  private final List<Operation> setIllTurnB = ImmutableList.<Operation>of(
      new Set(TURN, B),
      new Set(BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1)),
      new Set(W, ImmutableList.of(8,21,22,24)),
      new Set(B, ImmutableList.of(33,41,42,47,48,49)),     
      new Set(D, ImmutableList.of(
        1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
        25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46)));
  
  private final List<Operation> normalMoveOnRailB = ImmutableList.<Operation>of(
      new Set(TURN, W),
      new Set(BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          33,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1)),
      new Set(W, ImmutableList.of(8,21,22,24)),
      new Set(B, ImmutableList.of(33,41,42,47,48,49)),     
      new Set(D, ImmutableList.of(
        1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
        25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46)));
  
  private final List<Operation> normalMoveOnRailEB = ImmutableList.<Operation>of(
      new Set(TURN, W),
      new Set(BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,22,-1,-1,
          -1,-1,8,41,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,42,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,-1,
          -1,49,48,-1,-1)),
      new Set(W, ImmutableList.of(8,21,22,24)),
      new Set(B, ImmutableList.of(33,41,42,47,48,49)),     
      new Set(D, ImmutableList.of(
        1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,23,
        25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46))); 
  
  private final List<Operation> normalBeatB = ImmutableList.<Operation>of(
      new Set(TURN, W),
      new Set(BOARD,ImmutableList.of(
          -1,24,21,-1,-1,
          -1,-1,41,-1,-1,
          -1,-1,8,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          -1,-1,-1,-1,-1,
          33,-1,-1,-1,-1,
          -1,47,-1,-1,42,
          -1,49,48,-1,-1)),
      new Set(W, ImmutableList.of(8,21,24)),
      new Set(B, ImmutableList.of(33,41,42,47,48,49)),     
      new Set(D, ImmutableList.of(
        1,2,3,4,5,6,7,9,10,11,12,13,14,15,16,17,18,19,20,22,23,
        25,26,27,28,29,30,31,32,34,35,36,37,38,39,40,43,44,45,46)));
  

  private VerifyMove vMove(
      int playerId, Map<String, Object> state,
      int lastMovePlayerId, Map<String, Object> lastState, List<Operation> lastMove) {
    return new VerifyMove(playerId, playersInfo,
        state,
        lastState, lastMove, lastMovePlayerId);
  }
  
//  @SuppressWarnings("static-access")
//  private String placePiece(String slKey, String pKey){
//    int slId = Integer.valueOf(slKey.substring(2));
//    int i = slId / board.BOARD_ROW;
//    int j = slId % board.BOARD_COL;
//    if (pKey != null){
//      int pId = Integer.valueOf(pKey.substring(1));
//      if (pKey.charAt(0) == 'B')
//        board.placePiece(i, j, BH.get(pId));
//      else
//        board.placePiece(i, j, WH.get(pId));
//    }else{
//      board.placePiece(i, j, null);
//    }
//    return pKey;  
//  }
//   
//  private Board moveFromTo(int i1, int j1, int i2, int j2){    
//    return board;   
//  }

  private Piece genPieceFromId(String key, int pieceId){
    checkArgument(pieceId >= 0 && pieceId <25);
    Piece newPiece = new Piece(key);
    switch (pieceId){
      case 0:
        newPiece.setOrder(9);
        newPiece.setFace(PieceType.FIELDMARSHAL);
        break;
      case 1:
        newPiece.setOrder(8);
        newPiece.setFace(PieceType.GENERAL);
        break;
      case 2: case 3:
        newPiece.setOrder(7);
        newPiece.setFace(PieceType.MAJORGENERAL);
        break;
      case 4: case 5:
        newPiece.setOrder(6);
        newPiece.setFace(PieceType.BRIGADIERGENERAL);
        break;
      case 6: case 7:
        newPiece.setOrder(5);
        newPiece.setFace(PieceType.COLONEL);
        break;
      case 8: case 9:
        newPiece.setOrder(4);
        newPiece.setFace(PieceType.MAJOR);
        break;  
      case 10: case 11: case 12: 
        newPiece.setOrder(3);
        newPiece.setFace(PieceType.CAPTAIN);
        break;
      case 13: case 14: case 15: 
        newPiece.setOrder(2);
        newPiece.setFace(PieceType.LIEUTENANT);
        break;
      case 16: case 17: case 18: 
        newPiece.setOrder(1);
        newPiece.setFace(PieceType.ENGINEER);
        break;
      case 19: case 20:  
        newPiece.setOrder(0);
        newPiece.setFace(PieceType.BOMB);
        break;
      case 21: case 22: case 23: 
        newPiece.setOrder(0);
        newPiece.setFace(PieceType.LANDMINE);
        break;
      case 24: 
        newPiece.setOrder(0);
        newPiece.setFace(PieceType.FLAG);
        break;
      default: break;     
    }
    return newPiece;
  }

  @Test
  public void testInitialMove() {
    assertMoveOk(vMove(wId, initialState, bId, emptyState, initialMoveB));
  }

  @Test
  public void testInitialMoveByWrongPlayer() {
    assertHacker(vMove(bId, initialState , wId, emptyState, initialMoveB));
  }

  @Test
  public void testInitialMoveFromNonEmptyState() {
    assertHacker(vMove(wId, initialState ,bId, nonEmptyState, initialMoveB));
  }

  @Test
  public void testInitialMoveWithExtraOperation() {
    assertHacker(vMove(wId, initialState ,bId , emptyState, illInitialMoveWithExtraMovesB));
  }
  
  @Test
  public void testSetVB() {
    assertMoveOk(vMove(wId, beforeSetVAllB, bId, setVAllB, setVB));
  }
  
  @Test
  public void testIllSetVNoBB() {
    assertHacker(vMove(wId, initialState ,bId ,illSetVNoBB, setVB));
  }
  
  @Test
  public void testIllSetWLessBB() {
    assertHacker(vMove(wId, initialState ,bId ,illWlessB, setVB));
  }

  @Test
  public void testMoveB() {
    assertMoveOk(vMove(wId, oldStateB, bId,moveB , normalMoveB));
  }
  
  @Test
  public void testMoveOnRailB() {
    assertMoveOk(vMove(wId, oldStateB, bId, moveOnRailB, normalMoveOnRailB));
  }
  
  @Test
  public void testMoveOnRailEB() {
    assertMoveOk(vMove(wId, oldStateB, bId, moveOnRailEB, normalMoveOnRailEB));
  }
  
  @Test
  public void testBeatB() {
    assertMoveOk(vMove(wId, oldStateB, bId, beatB, normalBeatB));
  }

  @Test
  public void testIllTurnB() {
    assertHacker(vMove(wId, oldStateB, bId, moveB, setIllTurnB));
  } 
  
  @Test
  public void testIllVanishB() {
    assertHacker(vMove(wId, oldStateB, bId, illVanishB, normalMoveB));
  }
  
  @Test
  public void testIllDoubleMoveB() {
    assertHacker(vMove(wId, oldStateB, bId, illDoubleMoveB, normalMoveB));
  }
  
  @Test
  public void testIllNoPathB() {
    assertHacker(vMove(wId, oldStateB, bId, illNoPathB, normalMoveB));
  }
  
  @Test
  public void testIllRailTurnB() {
    assertHacker(vMove(wId, oldStateB, bId, illRailTurnB, normalMoveOnRailB));
  }
  
  @Test
  public void testIllRailToOutB() {
    assertHacker(vMove(wId, oldStateB, bId, illRailToOutB, normalMoveOnRailB));
  }
  
  @Test
  public void testIllFlagMoveB() {
    assertHacker(vMove(wId, oldStateB, bId, illFlagMoveB, normalMoveB));
  }
  
  @Test
  public void testIllLandmineMoveB() {
    assertHacker(vMove(wId, oldStateB, bId, illLandmineMoveB, normalMoveB));
  } 
  
  @Test
  public void testIllMoveThruRailB() {
    assertHacker(vMove(wId, oldStateB, bId, illMoveThruRailB, normalMoveOnRailB));
  }
  
  @Test
  public void testIllReOrderBeatB() {
    assertHacker(vMove(wId, oldStateB, bId, illReOrderBeatB, normalBeatB));
  }

  @Test
  public void testIllLandmineEnBeatB() {
    assertHacker(vMove(wId, oldStateB, bId, illLandmineEnBeatB, normalBeatB));
  }
  
  @Test
  public void testIllMoveWPieceB() {
    assertHacker(vMove(wId, oldStateB, bId, illMoveWPieceB, normalMoveB));
  }
  
}
