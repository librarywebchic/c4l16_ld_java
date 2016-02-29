package org.example;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class PrintValuesSpira {

	private static class Schema {
		private static Model m = ModelFactory.createDefaultModel();
		private static final String ns = "http://schema.org/";

		public static final Property name = m.createProperty(ns + "name");
		public static final Property author = m.createProperty(ns + "author");
		public static final Property creator = m.createProperty(ns + "creator");
		public static final Property about = m.createProperty(ns + "about");
		public static final Property description = m.createProperty(ns + "description");
	}

	public static void main(String[] args) {
		String uri = "http://www.worldcat.org/oclc/82671871";

		Model model = ModelFactory.createDefaultModel();
		model.read(uri);

		Resource subject = model.createResource(uri);

		StmtIterator names = model.listStatements(subject, Schema.name, (RDFNode)null);
		while (names.hasNext()) {
			Statement name = names.nextStatement();
			System.out.println(name.getObject().asLiteral().getString());
		}

		StmtIterator authors = model.listStatements(subject, Schema.author, (RDFNode)null);
		while (authors.hasNext()) {
			Resource author = authors.nextStatement().getObject().asResource();
			StmtIterator authorNames = model.listStatements(author, Schema.name, (RDFNode)null);
			while (authorNames.hasNext()) {
				System.out.println(authorNames.nextStatement().getObject().asLiteral().getString());
			}
		}

		StmtIterator creators = model.listStatements(subject, Schema.creator, (RDFNode)null);
		while (creators.hasNext()) {
			Resource creator = creators.nextStatement().getObject().asResource();
			StmtIterator creatorNames = model.listStatements(creator, Schema.name, (RDFNode)null);
			while (creatorNames.hasNext()) {
				System.out.println(creatorNames.nextStatement().getObject().asLiteral().getString());
			}
		}

		StmtIterator abouts = model.listStatements(subject, Schema.about, (RDFNode)null);
		while (abouts.hasNext()) {
			Resource about = abouts.nextStatement().getObject().asResource();
			StmtIterator aboutNames = model.listStatements(about, Schema.name, (RDFNode)null);
			if (aboutNames.hasNext()) {
				while (aboutNames.hasNext()) {
					System.out.println(aboutNames.nextStatement().getObject().asLiteral().getString());
				}
			} else {
				System.out.println(about.toString());
			}
		}

		StmtIterator descriptions = model.listStatements(subject, Schema.description, (RDFNode)null);
		while (descriptions.hasNext()) {
			System.out.println(descriptions.nextStatement().getObject().asLiteral().getString());
		}

	}

}
