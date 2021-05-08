package com.liangzhicheng.config.websocket;

import com.liangzhicheng.config.context.SpringContextHolder;
import com.liangzhicheng.modules.service.impl.TestDepartmentPersonServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description WebSocket管理相关类
 * @author liangzhicheng
 * @since 2021-04-27
 */
@Slf4j
@Component
@ComponentScan(basePackages = "com.liangzhicheng.modules.service.impl.TestDepartmentPersonServiceImpl") //解指定该类注入到bean中
@Order(value = 2) //区分先后顺序
@ServerEndpoint("/webSocket/{connectId}")
@Api(value="WebSocketManager", description="WebSocket")
public class WebSocketManager {

    private static TestDepartmentPersonServiceImpl departmentPersonService = SpringContextHolder.getBean(TestDepartmentPersonServiceImpl.class);

    /**
     * 用于存放所有在线客户端
     */
    public static Map<String, Session> clients = new ConcurrentHashMap<>();
    /**
     * 用于记录当前在线连接数，应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * 与某个客户端的连接会话，需要通过此来给客户端发送数据
     */
    private Session session;
    /**
     * 建立连接id（注：APP中userId或管理后台accountId）
     */
    private String connectId;

    @OnOpen
    public void onOpen(@PathParam("connectId") String connectId, Session session) throws IOException {
        this.connectId = connectId;
        this.session = session;
        addOnlineCount();
        clients.put(connectId, session);
        if(connectId.contains("personId:")){
            departmentPersonService.testOnlinePerson(connectId.substring(9));
        }
        log.warn("新的客户端上线，客户端id：{}", connectId);
    }

    @OnMessage
    public void onMessage(String param, Session session) throws IOException{
        log.info("收到客户端{}发来的消息", param);
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        subOnlineCount();
        clients.remove(connectId);
        log.info("客户端离线，客户端Id：{}", connectId);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        log.info("发生错误");
        throwable.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketManager.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketManager.onlineCount--;
    }

    public static synchronized Map<String, Session> getClients() {
        return WebSocketManager.getClients();
    }

}
