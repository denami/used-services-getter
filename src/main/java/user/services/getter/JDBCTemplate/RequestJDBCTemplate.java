package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;
import user.services.getter.services.RequestService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;

@Component
public class RequestJDBCTemplate implements RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestJDBCTemplate.class);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RequestStatus getRequestStatus(Request request) {
        return null;
    }

    @Override
    public void SetRequestStatus(Request request, RequestStatus requestStatus) {

    }

    @Override
    public Integer getRequestId(Request request) {
        return null;
    }

    @Override
    public Request getRequestById(Integer id) {
        String SQL = "SELECT id" +
                ",create_date_time" +
                ",status,start_date" +
                ",end_date" +
                ",requested_ip" +
                ",requested_domains" +
                ",description FROM getter_request WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{id}, new RequestRowMapper());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Request save(Request request) {
        Request r = getRequestByCreateDataTime(request.getCreateDateTime());
        if(r == null) {
            String SQL="INSERT INTO getter_request(" +
                    "create_date_time" +
                    ",status" +
                    ",start_date" +
                    ",end_date" +
                    ",requested_ip" +
                    ",requested_domains) VALUES (?,?,?,?,?,?)";
            jdbcTemplate.update(SQL,
                    request.getCreateDateTime(),
                    request.getStatus().toString(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getRequestedIpAddressComaList(),
                    request.getRequestedDomainAddressComaList());
        } else {
            String SQL = "UPDATE getter_request SET " +
                    "create_date_time=?" +
                    ", status = ?" +
                    ", start_date = ?" +
                    ", end_date = ?" +
                    ", requested_ip = ?" +
                    ", requested_domains = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(SQL,
                    request.getCreateDateTime(),
                    request.getStatus().toString(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getRequestedIpAddressComaList(),
                    request.getRequestedDomainAddressComaList(),
                    request.getId());
        }

        return getRequestByCreateDataTime(request.getCreateDateTime());
    }

    @Override
    public Collection<Request> getAllRequests() {
        String SQL = "SELECT id" +
                ",create_date_time" +
                ",status" +
                ",start_date" +
                ",end_date" +
                ",requested_ip" +
                ",requested_domains" +
                ",description FROM getter_request";

        Collection<Request> requests = new HashSet<>();

        try {
            requests.addAll(jdbcTemplate.query(SQL, new RowMapper<Request>() {
                @Override
                public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Request request = new Request();
                    request.setCreateDateTime(rs.getTimestamp("create_date_time").toLocalDateTime());
                    request.setStartDate(rs.getDate("start_date").toLocalDate());
                    request.setEndDate(rs.getDate("end_date").toLocalDate());
                    request.setId(rs.getInt("id"));
                    request.setStatus(RequestStatus.valueOf(rs.getString("status")));

                    Collection<String> ips = new HashSet<>();
                    Collection<String> domains = new HashSet<>();

                    String requested_domains = rs.getString("requested_domains");
                    String requested_ip = rs.getString("requested_ip");

                    if (requested_ip != null ) {
                        for (String s : rs.getString("requested_ip").split(",")) {
                            ips.add(s.trim());
                        }
                    }

                    if (requested_domains != null) {
                        for (String s : rs.getString("requested_domains").split(",")) {
                            domains.add(s.trim());
                        }
                    }

                    request.setRequestedDomainAddress(domains);
                    request.setRequestedIpAddress(ips);
                    return request;
                }
            }));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }

        return requests;
    }

    @Override
    public Request getRequestByCreateDataTime(LocalDateTime localDateTime) {
        String SQL = "SELECT id" +
                ",create_date_time" +
                ",status,start_date" +
                ",end_date" +
                ",requested_ip" +
                ",requested_domains" +
                ",description FROM getter_request WHERE create_date_time = ?";
        try {
            return jdbcTemplate.queryForObject(SQL, new Object[]{localDateTime.format(dtf)}, new RequestRowMapper());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public Collection<Request> getAllRequestsByStatus(RequestStatus requestStatus) {
        String SQL = "SELECT id" +
                ",create_date_time" +
                ",status" +
                ",start_date" +
                ",end_date" +
                ",requested_ip" +
                ",requested_domains" +
                ",description FROM getter_request WHERE status = ?";
        Collection<Request> requests = new HashSet<>();
        try {
            requests.addAll(jdbcTemplate.query(SQL, new Object[]{requestStatus.toString()}, new RequestRowMapper()));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return requests;
    }

    @Override
    public Request getRequestByStatus(RequestStatus requestStatus) {
        String SQL = "SELECT id" +
                ",create_date_time" +
                ",status" +
                ",start_date" +
                ",end_date" +
                ",requested_ip" +
                ",requested_domains" +
                ",description FROM getter_request WHERE status = ? LIMIT 1";
        return jdbcTemplate.queryForObject(SQL, new Object[]{requestStatus.toString()}, new RequestRowMapper());
    }

    private class RequestRowMapper implements RowMapper<Request> {

        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            Request request = new Request();
            request.setCreateDateTime(rs.getTimestamp("create_date_time").toLocalDateTime());
            request.setStartDate(rs.getDate("start_date").toLocalDate());
            request.setEndDate(rs.getDate("end_date").toLocalDate());
            request.setId(rs.getInt("id"));
            request.setStatus(RequestStatus.valueOf(rs.getString("status")));

            Collection<String> ips = new HashSet<>();
            Collection<String> domains = new HashSet<>();

            String requested_domains = rs.getString("requested_domains");
            String requested_ip = rs.getString("requested_ip");

            if (requested_ip != null) {
                for (String s : rs.getString("requested_ip").split(",")) {
                    ips.add(s.trim());
                }
            }

            if (requested_domains != null) {
                for (String s : rs.getString("requested_domains").split(",")) {
                    domains.add(s.trim());
                }
            }

            request.setRequestedDomainAddress(domains);
            request.setRequestedIpAddress(ips);
            return request;
        }
    }

}
