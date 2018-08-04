package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.RequestExecutionInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

@Component
public class RequestExecutionInfoJDBCTemplate {

    private static final Logger logger = LoggerFactory.getLogger(RequestExecutionInfoJDBCTemplate.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RequestExecutionInfo getInfoByRequestId(Integer id) {
        String SQL = "SELECT id, request_id, filesToExport, message FROM getter_execution_info " +
                "WHERE request_id = ? Limit 1";
        try {
            RequestExecutionInfo info = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new InfoRow());
            return info;
        } catch (EmptyResultDataAccessException e) {
            logger.info("No execution info found for request: {}", id);
        }
        return null;
    }

    public RequestExecutionInfo save(RequestExecutionInfo info) {

        String SQL = "REPLACE INTO getter_execution_info (request_id, filesToExport, message) " +
                "VALUES (?, ?, ?);";

        jdbcTemplate.update(SQL, new Object[]{
                info.getRequestId(),
                String.join(",", info.getNfFiles()),
                info.getMessage()});

        return getInfoByRequestId(info.getRequestId());
    }

    class InfoRow implements RowMapper<RequestExecutionInfo> {
        @Override
        public RequestExecutionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

            RequestExecutionInfo info = new RequestExecutionInfo();
            info.setRequestId(rs.getInt("request_id"));
            Collection<String> files = new HashSet<String>();

            for (String file : rs.getString("filesToExport").split(",")){
                files.add(file);
            }
            info.setNfFiles(files);
            info.setRequestId(rs.getInt("request_id"));
            info.setId(rs.getInt("id"));

            return info;
        }
    }
}
