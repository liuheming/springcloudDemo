# springcloudDemo
项目结构

父模块
springicloud

  子模块
  security
  mybatisplusgenerator
   
   
  一、security（用户认证及鉴权服务）
  由springSecurity+jtw+myBatisPlus构建 ，主要用于用户登入的认证 及 颁发用户凭证（jwt）  并为其他服务提供用户访问鉴权服务
  
  登陆地址 
  post /api/v1/auth/login
  form
    username
    password
    
  
  二、mybatisplusgenerator（mybatisPlus代码生成模块）
  
  
  
