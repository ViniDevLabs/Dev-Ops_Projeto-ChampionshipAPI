-- Inserir Times
INSERT INTO team (id, name, ranking_points) VALUES
('e37de10a-c6b3-4e6f-93fd-c2364dcf309c', 'Backend Warriors', 1200),
('4b6f8c13-5e85-4597-8de3-a7992f3960ff', 'Frontend Avengers', 1150),
('31f95b74-f569-4db1-a4c5-49c9a056e609', 'DevOps Titans', 1300),
('75f4ad1a-1500-4979-bc64-aaf43e41aa87', 'QA Guardians', 1100)
ON CONFLICT (id) DO NOTHING;

-- Inserir Jogadores para o time 'Backend Warriors' (ID: e37de10a-c6b3-4e6f-93fd-c2364dcf309c)
INSERT INTO player (id, name, age, position, hability_points, game_count, team_id) VALUES
('1d875dc1-a953-42ff-ad73-675ecb369a15', 'Ada Lovelace', 28, 'Atacante', 92, 15, 'e37de10a-c6b3-4e6f-93fd-c2364dcf309c'),
('cb0dbda4-47b6-4fcf-9fa7-6ce42ee5d2b3', 'Grace Hopper', 32, 'Meio-campo', 88, 20, 'e37de10a-c6b3-4e6f-93fd-c2364dcf309c')
ON CONFLICT (id) DO NOTHING;

-- Inserir Jogadores para o time 'Frontend Avengers' (ID: 4b6f8c13-5e85-4597-8de3-a7992f3960ff)
INSERT INTO player (id, name, age, position, hability_points, game_count, team_id) VALUES
('c305012f-7cbe-461f-813c-570e7655f855', 'Hedy Lamarr', 25, 'Atacante', 95, 12, '4b6f8c13-5e85-4597-8de3-a7992f3960ff'),
('62e262af-8f38-490b-957a-708d1042176d', 'Margaret Hamilton', 29, 'Defensor', 85, 18, '4b6f8c13-5e85-4597-8de3-a7992f3960ff')
ON CONFLICT (id) DO NOTHING;

-- Inserir Jogadores para o time 'DevOps Titans' (ID: 31f95b74-f569-4db1-a4c5-49c9a056e609)
INSERT INTO player (id, name, age, position, hability_points, game_count, team_id) VALUES
('de47de6f-bc4b-4039-8c55-3c1a3a22f061', 'Linus Torvalds', 30, 'Goleiro', 90, 25, '31f95b74-f569-4db1-a4c5-49c9a056e609'),
('a54e6d34-07a8-4e19-b68c-e0c624a7f932', 'James Gosling', 27, 'Meio-campo', 89, 22, '31f95b74-f569-4db1-a4c5-49c9a056e609')
ON CONFLICT (id) DO NOTHING;

-- Inserir Jogadores para o time 'QA Guardians' (ID: 75f4ad1a-1500-4979-bc64-aaf43e41aa87)
INSERT INTO player (id, name, age, position, hability_points, game_count, team_id) VALUES
('cebb971a-9609-441e-af34-6b146e7a2243', 'Radia Perlman', 26, 'Defensor', 91, 17, '75f4ad1a-1500-4979-bc64-aaf43e41aa87'),
('8a8b36e4-20a5-499d-8a69-bcafc586e099', 'Karen Spärck Jones', 31, 'Atacante', 87, 19, '75f4ad1a-1500-4979-bc64-aaf43e41aa87')
ON CONFLICT (id) DO NOTHING;

-- Inserir um Campeonato
INSERT INTO championship (id, name) VALUES
('30df12a1-936e-4012-b82e-0904fac619e1', 'Copa Devs 2025')
ON CONFLICT (id) DO NOTHING;

-- Inserir Jogos para o campeonato (ID: 30df12a1-936e-4012-b82e-0904fac619e1)
INSERT INTO game (id, home_team_goals, away_team_goals, date, away_team_id, championship_id, home_team_id) VALUES
('c1cab8e3-86aa-45c5-b2a0-a7049c77c25e', 2, 1, '2025-08-01', '4b6f8c13-5e85-4597-8de3-a7992f3960ff', '30df12a1-936e-4012-b82e-0904fac619e1', 'e37de10a-c6b3-4e6f-93fd-c2364dcf309c'),
('af9e0e85-c190-4149-a7dc-8db7a6c54faa', 3, 3, '2025-08-02', '75f4ad1a-1500-4979-bc64-aaf43e41aa87', '30df12a1-936e-4012-b82e-0904fac619e1', '31f95b74-f569-4db1-a4c5-49c9a056e609')
ON CONFLICT (id) DO NOTHING;

-- Inserir a Tabela do Campeonato (ID: 30df12a1-936e-4012-b82e-0904fac619e1)
INSERT INTO championship_table (id, championship_id) VALUES
('a0b1c2d3-e4f5-a6b7-c8d9-e0f1a2b3c4d5', '30df12a1-936e-4012-b82e-0904fac619e1')
ON CONFLICT (id) DO NOTHING;

-- Inserir as Entradas da Tabela (pontuação dos times no campeonato)
INSERT INTO table_entry (id, points, team_id, championship_table_id) VALUES
('1dad9281-a459-4b15-9253-6dad449a74d6', 3, 'e37de10a-c6b3-4e6f-93fd-c2364dcf309c', 'a0b1c2d3-e4f5-a6b7-c8d9-e0f1a2b3c4d5'),
('d217c92a-414e-4c29-8266-c9fe33615ff7', 0, '4b6f8c13-5e85-4597-8de3-a7992f3960ff', 'a0b1c2d3-e4f5-a6b7-c8d9-e0f1a2b3c4d5'),
('35e08f79-2b93-4003-b02c-d501bee850a7', 1, '31f95b74-f569-4db1-a4c5-49c9a056e609', 'a0b1c2d3-e4f5-a6b7-c8d9-e0f1a2b3c4d5'),
('4fb178c5-94f5-45cf-9d0c-3f157502fa47', 1, '75f4ad1a-1500-4979-bc64-aaf43e41aa87', 'a0b1c2d3-e4f5-a6b7-c8d9-e0f1a2b3c4d5')
ON CONFLICT (id) DO NOTHING;
