package org.it.learn.netty.mapper.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

import com.github.abel533.mapperhelper.EntityHelper;
import com.github.abel533.mapperhelper.MapperHelper;
import com.github.abel533.provider.MapperProvider;

public class WindyMapperProvider extends MapperProvider {

	public WindyMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public SqlNode deleteByIDs(MappedStatement ms) {
		final Class<?> entityClass = getSelectReturnType(ms);
		Set<EntityHelper.EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
		EntityHelper.EntityColumn entityColumn = null;
		for (EntityHelper.EntityColumn entity : entityColumns) {
			entityColumn = entity;
			break;
		}
		EntityHelper.EntityColumn column = entityColumn;
		List<SqlNode> sqlNodes = new ArrayList<>();

		// 开始拼SQL
		String sql = new SQL() {
			{
				// delete from table
				DELETE_FROM(tableName(entityClass));
			}
		}.toString();
		// WHERE id IN
		sqlNodes.add(new StaticTextSqlNode(sql + " WHERE " + column.getColumn() + " IN "));
		// 构造FOREACH SQL
		SqlNode foreach = new ForEachSqlNode(ms.getConfiguration(), new StaticTextSqlNode("#{" + column.getProperty() + "}"), "ids", "index", column.getProperty(), "(", ")", ",");
		sqlNodes.add(foreach);
		return new MixedSqlNode(sqlNodes);
	}

	public String insertList(MappedStatement ms) {
		final Class<?> entityClass = getSelectReturnType(ms);
		EntityHelper.EntityTable table = EntityHelper.getEntityTable(entityClass);
		// 开始拼sql
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(table.getName());
		sql.append("(");
		boolean first = true;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId()) {
				continue;
			}
			if (!first) {
				sql.append(",");
			}
			sql.append(column.getColumn());
			first = false;
		}
		sql.append(") VALUES ");
		sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
		sql.append("(");
		first = true;
		for (EntityHelper.EntityColumn column : table.getEntityClassColumns()) {
			if (column.isId()) {
				continue;
			}
			if (!first) {
				sql.append(",");
			}
			sql.append("#{record.").append(column.getProperty()).append("}");
			first = false;
		}
		sql.append(")");
		sql.append("</foreach>");
		return sql.toString();
	}
}
