package com.devops_jp_vc.championshipapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops_jp_vc.championshipapi.model.Player;
import com.devops_jp_vc.championshipapi.repository.PlayerRepository;

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
