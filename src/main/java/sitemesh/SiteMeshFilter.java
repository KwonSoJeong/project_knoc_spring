package sitemesh;

import javax.servlet.annotation.WebFilter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

@WebFilter("/*")
public class SiteMeshFilter extends ConfigurableSiteMeshFilter{
	
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		//builder.addDecoratorPath("/*", "/layout/head.jsp").addExcludedPath("/single/*");
		//builder.addDecoratorPath("/*", "/layout/layout.jsp").addExcludedPath("/single/*");;
		builder.addDecoratorPath("/*", "/layout/layout.jsp")
		.addDecoratorPath("/admin/*", "/layout/adNavigation.jsp")
		.addExcludedPath("/layout/adNavigation.jsp")
		.addExcludedPath("/*/adminChat")
		.addExcludedPath("/*/classFavorite")
		.addExcludedPath("/*/imgUpload")
		.addExcludedPath("/*/picture*")
		.addExcludedPath("/*/single*")
		.addExcludedPath("/*/classList2")
		.addExcludedPath("/*/thumbnail*")
		.addExcludedPath("/*/*Chk");
	}
}
