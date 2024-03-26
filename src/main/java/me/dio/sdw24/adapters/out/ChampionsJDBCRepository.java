package me.dio.sdw24.adapters.out;

import me.dio.sdw24.domain.model.Champions;
import me.dio.sdw24.domain.ports.ChampionsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class ChampionsJDBCRepository implements ChampionsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Champions> championsRowMapper;

    public ChampionsJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.championsRowMapper = (rs, rowNum) -> new Champions(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("lore"),
                rs.getString("image_Url")
        );
    }


    @Override
    public List<Champions> findAll() {
        return jdbcTemplate.query("SELECT * FROM CHAMPIONS", championsRowMapper);
    }

    @Override
    public Optional<Champions> findById(int idChampion) {
        String sql = "SELECT * FROM CHAMPIONS WHERE id = ?";
        Champions champion = jdbcTemplate.queryForObject(sql, championsRowMapper, idChampion);
        return Optional.ofNullable(champion);
    }
}
