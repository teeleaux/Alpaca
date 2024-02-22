package no.uio.ifi.in2000.tiffanrl.oblig2.model.alpacas

data class PartyInfo (
    val id: String,
    val name: String,
    val leader: String,
    val img: String,
    val color: String,
    val description: String
) {
    /*companion object { // null safety companion object which would make a default party instead of letting a party be null
        fun default(): PartyInfo{
            return PartyInfo(
                id = "Default",
                name = "Default name",
                leader = "Tiffany",
                img = "",
                color = "0xFF7D5260",
                description = "fubar"
            )
        }
    }*/
}

