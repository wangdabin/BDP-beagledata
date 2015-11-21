手动指定副本以及机架感知安装说明:
1、打包proj，放置于各节点的 /opt/hadoop/share/hadoop/hdfs/lib/（cdh4.1.2为例）目录下。
2、机架感知配置
	a,在hdfs-site.xml添加配置项如下:
	 <property>
   		<name>topology.node.switch.mapping.impl</name>
   		<value>org.apache.hadoop.hdfs.server.blockmanagement.RackAware</value>
 	 </property>
	
	b,在hdfs-site.xml添加配置项rackaware.conf.xml 以指定 机架配置文件rack.xml的路径。
	<property>
   		<name>rackaware.conf.xml</name>
   		<value>/opt/hadoop/etc/hadoop/rack.xml</value>//默认安装路径
 	 </property>
	c,机架配置文件参考本proj的conf目录下rack.xml文件
3、副本手工策略配置
	a,在hdfs-site.xml添加配置项如下:
	 <property>
     	<name>dfs.block.replicator.classname</name>
    	<value>org.apache.hadoop.hdfs.server.blockmanagement.SkyCloudBlockPlacementPolicyDefault</value>
 	 </property>
	
	b,在hdfs-site.xml添加配置项blockplacementpolicy.conf.xml 以指定副本手工策略配置文件replicaPloicy.xml的路径。
	<property>
   		<name>blockplacementpolicy.conf.xml</name>
   		<value>/opt/hadoop/etc/hadoop/replicaPloicy.xml</value>//默认安装路径
 	 </property>
	c,副本手工策略配置文件参考本proj的conf目录下replicaPloicy.xml文件.
4、拷贝各个配置文件到其他节点
5、重启hdfs服务