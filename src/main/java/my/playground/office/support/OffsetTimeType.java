package my.playground.office.support;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.CustomType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import static java.sql.Types.TIME_WITH_TIMEZONE;

public class OffsetTimeType extends CustomType {

    private static final UserType USER_TYPE = new UserType() {

        @Override
        public int[] sqlTypes() {
            return new int[]{TIME_WITH_TIMEZONE};
        }

        @Override
        public Class returnedClass() {
            return OffsetTime.class;
        }

        @Override
        public boolean equals(Object x, Object y) {
            return x.equals(y);
        }

        @Override
        public int hashCode(Object x) {
            return x.hashCode();
        }

        @Override
        public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws SQLException {
            String timeStr = rs.getString(names[0]);
            if (timeStr == null) {
                return null;
            }
            return OffsetTime.parse(toISO(timeStr), DateTimeFormatter.ISO_OFFSET_TIME);
        }

        @Override
        public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws SQLException {
            if (value != null) {
                OffsetTime time = (OffsetTime) value;
                String timeStr = time.format(DateTimeFormatter.ISO_OFFSET_TIME);
                st.setString(index, fromISO(timeStr));
            } else {
                st.setNull(index, TIME_WITH_TIMEZONE);
            }
        }

        @Override
        public Object deepCopy(Object value) {
            return value;
        }

        @Override
        public boolean isMutable() {
            return false;
        }

        @Override
        public Serializable disassemble(Object value) throws HibernateException {
            return (OffsetTime) value;
        }

        @Override
        public Object assemble(Serializable cached, Object owner) {
            return cached;
        }

        @Override
        public Object replace(Object original, Object target, Object owner) {
            return original;
        }

        private CharSequence toISO(CharSequence time) {
            StringBuilder sb = new StringBuilder(time.length() + 2);
            int dcount = 0;
            for (int i=0; i < time.length(); i++) {
                char c = time.charAt(i);
                if (Character.isDigit(c)) {
                    dcount++;
                    sb.append(c);
                    continue;
                }
                if (dcount == 1) {
                    sb.insert(sb.length() - 1, '0');
                }
                dcount = 0;
                sb.append(c);
            }
            return sb;
        }

        private String fromISO(String timeStr) {
            return timeStr.replace("Z", "+00:00");
        }
    };

    public OffsetTimeType() throws MappingException {
        super(USER_TYPE);
    }

}
