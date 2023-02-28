import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;

public class RedisStorage {
    private RedissonClient redissonClient;

    private RScoredSortedSet<Integer> datingUsers;
    private final static String KEY = "USERS";

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redissonClient = Redisson.create(config);
        } catch (RedisConnectionException e) {
            e.printStackTrace();
        }
        datingUsers = redissonClient.getScoredSortedSet(KEY);
        datingUsers.clear();
    }

    private double getTimeStamp() {
        return new Date().getTime() / 1000;
    }

    public void registerUsers() {
        for (int i = 1; i <= 20; i++) {
            datingUsers.add(getTimeStamp(), i);
        }
    }

    public RScoredSortedSet<Integer> getDatingUsers() {
        return datingUsers;
    }
}
