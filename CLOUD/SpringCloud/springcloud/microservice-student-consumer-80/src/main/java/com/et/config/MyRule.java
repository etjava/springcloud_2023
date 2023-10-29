//package com.et.config;
//
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.Server;
//
//import java.util.List;
//
//public class MyRule extends AbstractLoadBalancerRule {
//
//    private int total = 0;
//    private int index = 0;
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//
//    }
//    @Override
//    public Server choose(Object o) {
//        return this.choose(this.getLoadBalancer(),o);
//    }
//
//    public Server choose(ILoadBalancer lb, Object key) {
//        System.out.println("=====================Ribbon自定义规则");
//        if (lb == null) {
//            return null;
//        } else {
//            Server server = null;
//
//            while (server == null) {
//                if (Thread.interrupted()) {
//                    return null;
//                }
//
//                List<Server> upList = lb.getReachableServers();//获取可获得到的服务，即没有故障可以运行的服务
//                List<Server> allList = lb.getAllServers();//获取所有服务
//                int serverCount = allList.size();
//                if (serverCount == 0) {
//                    return null;
//                }
//
//                //自定义负载均衡方法，每个服务执行3次，依次轮询
//                if (total<3){
//                    server = (Server) upList.get(index);//根据索引从可获得的服务中获取服务
//                    total++;
//                }else {
//                    total=0;
//                    if (index>=upList.size()-1){
//                        index=0;
//                    }else {
//                        index++;
//                    }
//                }
//
//                if (server == null) {
//                    Thread.yield();
//                } else {
//                    if (server.isAlive()) {
//                        return server;
//                    }
//
//                    server = null;
//                    Thread.yield();
//                }
//            }
//
//            return server;
//        }
//    }
//}