//build xml
def writer = new StringWriter()
def builder = new groovy.xml.MarkupBuilder(writer)
builder.setDoubleQuotes(true)
builder.todos {
	todo (id:"1") {
		name "Buy Beginning Groovy and Grails"
		note "Purchase book from Amazon.com for all co-workers."
	}
}
println writer.toString()
//save xml
def xmlCompleteFilePath = '/projects/groovy/apress/todos.xml'
def file = new File(xmlCompleteFilePath)
file.delete()
file.createNewFile (  )
file.write(writer.toString())
//file.close()

//retrieve xml and parse it
def todos = new XmlSlurper().parse(xmlCompleteFilePath)
assert 1 == todos.todo.size()
assert "Buy Beginning Groovy and Grails" == todos.todo[0].name.text()
assert "1" == todos.todo[0].@id.text()