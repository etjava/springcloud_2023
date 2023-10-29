// 服务拆分之前只需要调用同一端口
/* export function genServerUrl(path){
  /!* path 具体的服务端请求接口路径 *!/
  return "http://localhost:80/"+path;
} */

// 服务拆分后 需要根据不同业务调用不同服务 - 配置网关之前
/* export function genServerUrl(path,model){
  /!* path 具体的服务端请求接口路径 *!/
  if(model=="user"){ // 用户模块
    return "http://localhost:81/user/"+path;
  } else if(model=="miaoShaGoods"){
    return "http://localhost:8081/miaoShaGoods/"+path;
  } else if(model=="image"){
    return "http://localhost:8081/"+path;
  } else if(model=="verifyCode"){
    return "http://localhost:8083/verifyCode/"+path;
  } else if(model=="miaoSha"){
    return "http://localhost:8083/miaoSha/"+path;
  } else if(model=="order"){
    return "http://localhost:8082/order/"+path;
  } else if(model=="token"){
    return "http://localhost:81/token/"+path;
  } */

// 配置网关之后 - 所有请求都需要走网关 因此端口定义为统一的 3001
export function genServerUrl(path,model){
  /* path 具体的服务端请求接口路径 */
  if(model=="user"){ // 用户模块
    return "http://localhost:3001/user/"+path;
  } else if(model=="miaoShaGoods"){
    return "http://localhost:3001/miaoShaGoods/"+path;
  } else if(model=="image"){
    return "http://localhost:3001/"+path;
  } else if(model=="verifyCode"){
    return "http://localhost:3001/verifyCode/"+path;
  } else if(model=="miaoSha"){
    return "http://localhost:3001/miaoSha/"+path;
  } else if(model=="order"){
    return "http://localhost:3001/order/"+path;
  } else if(model=="token"){
    return "http://localhost:3001/token/"+path;
  }
}

/**
 * 改回最初的拼装模式
 * 这里还需要调整各个组件中调用的地方
 * @param path
 * @returns {string}
 */
/* export function genServerUrl(path){
  /!* path 具体的服务端请求接口路径 *!/
  return "http://localhost:80/"+path;
} */
