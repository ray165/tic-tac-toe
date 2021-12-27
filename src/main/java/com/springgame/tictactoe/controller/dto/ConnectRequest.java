package com.springgame.tictactoe.controller.dto;

import com.springgame.tictactoe.model.Player;

import lombok.Data;

@Data
public class ConnectRequest {
    private Player player;
    private String gameId;
    
}
