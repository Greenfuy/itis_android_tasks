package com.itis.itistasks.data

object Questions {
    private val questions: List<Question> = listOf(
        Question("Which planet is known as the Red Planet?", listOf("Mars", "Venus", "Earth", "Jupiter", "Saturn")),
        Question("What is the capital of France?", listOf("Paris", "Madrid", "Rome", "Berlin", "London")),
        Question("Who wrote the play 'Romeo and Juliet'?", listOf("William Shakespeare", "Jane Austen", "Charles Dickens", "George Orwell", "F. Scott Fitzgerald")),
        Question("In which year did Christopher Columbus first voyage to the Americas?", listOf("1492", "1620", "1776", "1812", "1950", "2001")),
        Question("What is the largest mammal in the world?", listOf("Blue Whale", "African Elephant", "Giraffe", "Hippopotamus", "Gorilla", "Kangaroo")),
        Question("Which gas do plants absorb from the atmosphere during photosynthesis?", listOf("Carbon Dioxide", "Oxygen", "Nitrogen", "Methane", "Argon", "Helium")),
        Question("What is the chemical symbol for gold?", listOf("Au", "Ag", "Fe", "Cu", "Hg", "Pb", "Na")),
        Question("Who is the author of 'To Kill a Mockingbird'?", listOf("Harper Lee", "J.K. Rowling", "George Orwell", "Mark Twain", "F. Scott Fitzgerald", "Charles Dickens")),
        Question("Which country is known as the Land of the Rising Sun?", listOf("Japan", "China", "South Korea", "India", "Thailand", "Vietnam")),
        Question("What is the main component of Earth's atmosphere?", listOf("Nitrogen", "Oxygen", "Carbon Dioxide", "Argon", "Methane", "Helium", "Krypton"))
    )

    fun getQuestions() : List<Question> {
        return questions
    }
}
