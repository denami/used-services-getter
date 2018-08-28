package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.Abonent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class AbonentJDBCTemplate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FileDateTimeMapperJDBCTemplate.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Abonent getAbonentById(Integer id) {
        String SQL = "SELECT abn_id, abn_name, abn_address FROM abonents WHERE abn_id= ? limit 1";

        try {
            Abonent abonent = jdbcTemplate.queryForObject(SQL, new Object[]{id}, new AbonentRow());
            return abonent;
        } catch (Exception e) {
            logger.error("Can`t receive abonent ", e);
        }

        return null;
    }

    public Collection<Abonent> getAllAbonents() {
        String SQL = "SELECT abn_id, abn_name, abn_address FROM abonents";

        try {
            List<Abonent> abonentsList = jdbcTemplate.query(SQL, new AbonentRow());
            Collection<Abonent> abonents = new HashSet<Abonent>(abonentsList);
        } catch (Exception e) {
            logger.error("Can`t receive abonents ", e);
        }

        return null;
    }


    class AbonentRow implements RowMapper<Abonent> {
        @Override
        public Abonent mapRow(ResultSet rs, int rowNum) throws SQLException {
            Abonent abonent = new Abonent(rs.getInt("abn_id")
                    , rs.getString("abn_name")
                    , rs.getString("abn_address"));
            return abonent;
        }
    }

}