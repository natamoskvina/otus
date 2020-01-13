import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

public class ResultSender {

    private static final InfluxDB INFLXUDB = InfluxDBFactory.connect("http://192.168.99.100:8086", "root", "root");
    private static final String DATABASE = "selenium";

    static{
        INFLXUDB.setDatabase(DATABASE);
    }

    public static void send(final Point point){
        INFLXUDB.write(point);
    }

}
