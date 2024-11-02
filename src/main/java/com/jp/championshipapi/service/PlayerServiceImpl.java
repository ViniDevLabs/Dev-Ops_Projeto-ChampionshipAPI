package com.jp.championshipapi.service;

import com.jp.championshipapi.dto.PlayerDTO;
import com.jp.championshipapi.model.Player;
import com.jp.championshipapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player findById(UUID id) {
        return playerRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public Player create(Player player) {
        return playerRepository.save(player);
    }
}
