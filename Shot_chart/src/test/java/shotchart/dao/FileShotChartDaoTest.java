package shotchart.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import shotchart.domain.FakeShotChartDao;
import shotchart.domain.FakeUserDao;
import shotchart.domain.ShotChart;
import shotchart.domain.User;

public class FileShotChartDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File userFile;
    File shotChartFile;
    ShotChartDao scDao;
    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userFile = testFolder.newFile("testfile_users.txt");
        shotChartFile = testFolder.newFile("testfile_shotcharts.txt");

        try (FileWriter file = new FileWriter(userFile.getAbsolutePath())) {
            file.write("botnia;antti123\n");
        }

        //UserDao userDao = new FakeUserDao();
        //userDao.create(new User("botnia", "antti123"));
        userDao = new FileUserDao(userFile.getAbsolutePath());
        scDao = new FileShotChartDao(shotChartFile.getAbsolutePath(), userDao);
        scDao.create(new ShotChart("2019-02-01", "tps", userDao.findByUsername("botnia")));

    }

    @Test
    public void shotChartsAreReadCorrectlyFromFile() {
        List<ShotChart> shotcharts = scDao.getAll();
        assertEquals(1, shotcharts.size());
        ShotChart shotchart = shotcharts.get(0);
        assertEquals("2019-02-01", shotchart.getDate());
        assertEquals("tps", shotchart.getOpponent());
        assertEquals(1, shotchart.getId());
        assertEquals("botnia", shotchart.getUser().getUsername());
    }

    
    @Test
    public void createdShotChartsAreListed() throws Exception {
        scDao.create(new ShotChart("2019-04-04", "KyrPa", userDao.findByUsername("botnia")));

        List<ShotChart> shotcharts = scDao.getAll();
        assertEquals(2, shotcharts.size());
        ShotChart shotchart = shotcharts.get(1);
        assertEquals("2019-04-04", shotchart.getDate());
        assertEquals("KyrPa", shotchart.getOpponent());
        assertNotEquals(1, shotchart.getId());
        assertEquals("botnia", shotchart.getUser().getUsername());
    }
    
 
    @Test
    public void getChartReturnsCorrectChart() throws Exception {
        ShotChart lukko = scDao.create(new ShotChart("2019-04-04", "KyrPa", userDao.findByUsername("botnia")));
        assertEquals(lukko, scDao.getChart(2));
        assertEquals(null, scDao.getChart(10));
    }
    
    @Test
    public void updatingChartUpdatesCorrectly() throws Exception {
        ShotChart sc = scDao.getChart(1);
        sc.setOpponent("tappara");
        scDao.update(sc);
        assertEquals("tappara", scDao.getChart(1).getOpponent());
    }
 
    @Test
    public void deletingChartRemovesChart() throws Exception {
        ShotChart hpk = scDao.create(new ShotChart("2018-10-04", "HPK", new User("pori", "pata")));
        ArrayList<ShotChart> scs = scDao.getAll();
        scDao.delete(scs.get(0));
        assertEquals(1, scs.size());
        assertEquals(hpk, scs.get(0));
    }

    @After
    public void tearDown() {
        userFile.delete();
    }
}
