package sg.nus.iss.day23workshop2.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.nus.iss.day23workshop2.model.Video;

@Repository
public class VideoRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private String findAllSQL = "select * from video";
    private String insertVideoSQL = "insert into video values (null, ?, ?, ?)";
    private String updateVideoSQL = "update video set title = ?, synopsis = ?, available_count = ? where id = ?";

    public List<Video> findAll(){
        return jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(Video.class));
    }

    public int updateVideo (Video video){
        return jdbcTemplate.update(updateVideoSQL, video.getTitle(), video.getSynopsis(), video.getAvailableCount(), video.getId());
    }

    public int insertVideo(Video video){
        return jdbcTemplate.update(insertVideoSQL,video.getTitle(), video.getSynopsis(), video.getAvailableCount());
    }
}
