# 乌鸦社区
这是一个问答社区。
  
## 资料
[BootStrap 开发手册](https://getbootstrap.com/docs/4.3/getting-started/introduction/)  
[Github OAuth API](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[Elasticsearch 中文社区](https://elasticsearch.cn/)

## 工具
Spring Boot IDEA Git BootStrap

## 脚本
```sql
create table USER
(
	ID INTEGER default NEXT VALUE primary key,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFY BIGINT
);
```




