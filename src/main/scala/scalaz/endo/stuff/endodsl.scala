package scalaz.endo.stuff

/**
 * Example from the following blog post on creating a DSL for an API using Writer and Endo.
 * Endo is also a Monoid so can be used by Writer to accumulate Tasks within a Project in 
 * the sample implementation below. Endo itself represents an endomorphism. An endomorphism
 * is simply a function that takes an argument of type T and returns the same type T.
 * http://debasishg.blogspot.com.au/2013/02/a-dsl-with-endo-monoids-for-free.html
 */

import scalaz._
import scalaz.Scalaz._
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
                     deps: List[(Task, Task)] = List()) {
    override def toString = {
      name + ", " + startDate + ", " + endDate + "\n" + "Tasks: " + tasks.mkString(", ") + "\n" + "Dependencies: " + deps.mkString(", ")
    }
  }
  
                     
  val withTask = (t: Task, p: Project) => p.copy(tasks = t :: p.tasks)
  val withDependency = (t: Task, on: Task, p: Project) => p.copy(deps = (t, on) :: p.deps)
  
  
  // defining the dsl here...
  
  def task(n: String): Writer[Endo[Project], Task] = {
    val t = Task(n)
    for {
      _ <- WriterT.tell( ((p: Project) => withTask(t, p)).endo )
    } yield t
  }
  
  def project(name: String, startDate: Date)(e: Writer[Endo[Project], Task]) = {
    val p = Project(name, startDate)
    //the following are equivalent - written just does run then _1.
    //e.run._1(p)
    e.written.run(p)
  }
}


// Using the DSL in the main program to accumulate tasks in a project.

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