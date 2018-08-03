--
-- Created by IntelliJ IDEA.
-- User: gdy
-- Date: 2018/8/3
-- Time: 13:44
-- To change this template use File | Settings | File Templates.
--
local opration = tonumber(ARGV[1]);

if opration == 0 then
    --    lock
    if redis.call('SET', KEYS[1], ARGV[2], ARGV[3], ARGV[4], "NX") then
        return true
    else
        return false
    end
else
    if (redis.call('GET', KEYS[1]) == ARGV[2]) then
        if opration == 1 then
            --            sustain lock
            if redis.call("SET", KEYS[1], ARGV[2], ARGV[3], ARGV[4]) then
                return true
            end
        else
            --            release lock
            if redis.call('del', KEYS[1]) then
                return true
            end
        end
    end
    return false
end
