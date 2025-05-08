import com.mongodb.client.{MongoClients, MongoCollection}
import org.bson.Document
import scala.io.StdIn
import play.api.libs.json._



object Insertar_MongoDB 
{

  def main(args: Array[String]): Unit = 
  {

    // Crear cliente y conectarse a la base de datos
    val mongoClient = MongoClients.create("mongodb://localhost:27017")
    val database = mongoClient.getDatabase("libros")
    val collection: MongoCollection[Document] = database.getCollection("libros")

    // Solicita introducir autor
    print("Introduce el autor:")
    val autor = StdIn.readLine()

    // Solicita introducir título
    print("Introduce el título:")
    val titulo = StdIn.readLine()

    // Crear el documento BSON para MongoDB
    val documento = new Document()
      .append("autor", autor)
      .append("titulo", titulo)
    

    // Insertar en la colección
    collection.insertOne(documento)

    println("Documento insertado correctamente.")


    // Cierra cliente
    
    



  }

}
