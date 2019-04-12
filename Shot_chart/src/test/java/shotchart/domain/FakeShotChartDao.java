package shotchart.domain;

import java.util.ArrayList;
import java.util.List;
import shotchart.dao.ShotChartDao;

public class FakeShotChartDao implements ShotChartDao {

    List<ShotChart> shotcharts;

    public FakeShotChartDao() {
        shotcharts = new ArrayList<>();
    }

    @Override
    public List<ShotChart> getAll() {
        return shotcharts;
    }

    @Override
    public ShotChart create(ShotChart shotchart) {
        shotchart.setId(shotcharts.size() + 1);
        shotcharts.add(shotchart);
        return shotchart;
    }

    @Override
    public ShotChart update(ShotChart shotChart) {
        int index = -1;
        for (int i = 0; i < shotcharts.size(); i++) {
            if (shotcharts.get(i).getId() == shotChart.getId()) {
                index = i;
            }
        }
        if (index != -1) {
            shotcharts.remove(index);
            shotcharts.add(shotChart);
        }
        return shotChart;
    }
}
