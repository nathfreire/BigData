
import com.mongodb.client.{MongoClients, MongoCollection}
import org.bson.Document
import scala.io.StdIn
import upickle.default.{read => uread, _}
import os._

//import play.api.libs.json._   no se necesita porque no se necesita ya que se usa Document
import org.bson.types.ObjectId

// Necesitamos crear una clase para el libro para que SCALA sepa qué estructura construir, que es
// un objeto (¿qué campos? ¿qué tipos de datos?)
case class Libro(titulo: String, autor: String, fecha_publicacion: String)
implicit val libroRW: upickle.default.ReadWriter[Libro] = upickle.default.macroRW[Libro]


object Fichero_MongoDB 
{
  def main(args: Array[String]): Unit = 
  {
    val mongoClient = MongoClients.create("mongodb://localhost:27017")
    val database = mongoClient.getDatabase("miBiblioteca3")

    // creacion de la coleccion libros
    val mislibros: MongoCollection[Document] = database.getCollection("libros") 
    //es más explícita por lo que cualquier error te avisará en la compilación en vez de en la ejecución
    val misautores: MongoCollection[Document] = database.getCollection("autores")



    // Ubicar dónde está el JSON y leerlo
    val rutaJson = os.pwd /"libros.json"
    val contenidoJson = os.read(rutaJson)

    //Convertir el JSON a una estructura que el programa entienda, como en
    // este caso usamos SCALA, hay que convertirlo a un objeto SCALA. Esto
    // es parsear, y, para lo cual SCALA necesita saber qué estructura debe
    // construir, lo cual se hace con la clase Libro que hemos creado antes.

    val librosJson: List[Libro] = upickle.default.read[List[Libro]](contenidoJson)



    // Insertar cada libro
    for (i <- librosJson) 
    {
        // Obtener el autor del libro
        val autorNombre= i.autor
        //(val autorNombre = libroData("autor")) ????? por qué no esta línea? 
        
        val autorExistente = misautores.find(new Document("nombre", autorNombre)).first()
        // el libro con el autor existente/nuevo y el título

        val autorId: ObjectId = if
        (autorExistente != null) 
        {
            autorExistente.getObjectId("_id")
        } 
        else 
        {
            // crea una nueva variable en la que se va aguardar el autor introducido su id
            val nuevoAutor = new Document("nombre", autorNombre)
            

            // Insertar el nuevo autor en la colección de autores
            misautores.insertOne(nuevoAutor)
            nuevoAutor.getObjectId("_id") //creo que no es necesario 
            // porque MongoDB ya lo crea automáticamente

        }


        // Obtener el titulo porque eso se va a insertar en la colección de libros
        val nombreLibros = i.titulo
        val nuevoTitulo = new Document()
            .append("titulo", nombreLibros)
            .append("autor", autorId)

            //Insertar el nuevo titulo en la colección de libros/titulos de MonfoDB
            mislibros.insertOne(nuevoTitulo)

            
    
            println(s"Libro '$nombreLibros' insertado correctamente.")
    }


    // Cerrar la conexión a la base de datos
  }

}