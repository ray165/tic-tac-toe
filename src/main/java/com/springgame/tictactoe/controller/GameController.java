package com.springgame.tictactoe.controller;

import com.springgame.tictactoe.controller.dto.ConnectRequest;
import com.springgame.tictactoe.exception.InvalidGameException;
import com.springgame.tictactoe.exception.InvalidParamsException;
import com.springgame.tictactoe.exception.NotFoundException;
import com.springgame.tictactoe.model.Game;
import com.springgame.tictactoe.model.GamePlay;
import com.springgame.tictactoe.model.Player;
import com.springgame.tictactoe.service.GameService;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) {
        log.info("start game request : {}", player);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws InvalidGameException, InvalidParamsException{
        log.info("connect request: {}", request);
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));
    }

    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws NotFoundException {
        log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) {
        log.info("gameplay: {}", request);
        Game game = gameService.gamePlay(request);
        SimpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameId(), game);
        SimpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameId(), null);
        
        return ResponseEntity.ok(game);
        
    }
}
