package scalaz.stuff

//http://debasishg.blogspot.com.au/2013/02/a-dsl-with-endo-monoids-for-free.html

import scalaz._
import Scalaz._
import java.util.{Date, Calendar}

object endodsl {
  case class Task(name: String) {
    def dependsOn(on: Task): Writer[Endo[Project], Task] = {
      for {
        _ <- WriterT.tell( ((p: Project) => withDependency(this, on, p)).endo )
      } yield this
    }
  }
  
  case class Project(name: String,
                     startDate: Date,
                     endDate: Option[Date] = None,
                     tasks: List[Task] = List(),
                     deps: List[(Task, Task)] = List())
  
  val withTask = (t: Task, p: Project) => p.copy(tasks = t :: p.tasks)
  val withDependency = (t: Task, on: Task, p: Project) => p.copy(deps = (t, on) :: p.deps)
  
  def task(n: String): Writer[Endo[Project], Task] = {
    val t = Task(n)
    for {
      _ <- WriterT.tell( ((p: Project) => withTask(t, p)).endo )
    } yield t
  }
  
  def project(name: String, startDate: Date)(e: Writer[Endo[Project], Task]) = {
    val p = Project(name, startDate)
    e.run._1(p)
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    import endodsl._
    val now = Calendar.getInstance().getTime()
    
    val p = project("xenos", now) {
      for {
        a <- task("study requirements")
        b <- task("do analysis")
        _ <- b dependsOn a
        c <- task("design & code")
        _ <- c dependsOn b
        d <- c dependsOn a
      } yield d
    }
    
    println(p)
  }
}