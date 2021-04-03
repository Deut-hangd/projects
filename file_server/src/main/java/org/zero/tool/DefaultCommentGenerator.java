package org.zero.tool;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import java.util.Properties;
// 自定义mybatis注解生成器
public class DefaultCommentGenerator implements CommentGenerator {
    @Override
    public void addConfigurationProperties(Properties properties) {
    }

    // 为生成的模型类的字段(属性)添加注释
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if(introspectedColumn.isIdentity())
            field.addJavaDocLine("");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            StringBuilder sb = new StringBuilder();
            sb.append(" * ");
            sb.append(introspectedColumn.getRemarks());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" */");
        }
    }
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    // 为生成的模型类添加注释
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        if (StringUtility.stringHasValue(introspectedTable.getRemarks())) {
            topLevelClass.addJavaDocLine("/**");
            topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
            topLevelClass.addJavaDocLine(" */");
        }
    }
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable,
                                boolean markAsDoNotDelete) {
    }
    @Override
    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
    }
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }
    @Override
    public void addComment(XmlElement xmlElement) {
    }
    @Override
    public void addRootComment(XmlElement rootElement) {
    }
}


