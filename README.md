# 双“13” 电商秒杀系统
### 项目技术点：
- SpringBoot
- MySQL
- Redis
- RabbitMQ
- JMeter
- MyBatisPlus
- Lombok
- Thymeleaf

### 项目亮点：
- 核心架构：基于SpringBoot构建高并发秒杀系统，采用MVC分层架构，通过Docker实现Redis与RabbitMQ的容器化独立部署，避免资源竞争并提升服务的可维护性。
- 性能优化：引入Redis缓存，针对热点数据，采用“预减库存+内存标记”机制，结合Lua脚本保证原子性操作，单接口QPS从2300提升至12515。
- 异步削峰：集成RabbitMQ实现异步下单流程，利用队列削峰填谷，进一步减轻接口的压力。
- 开发效率：结合LomBok注解与MyBatis-Plus自动生成代码，减少重复性劳动
