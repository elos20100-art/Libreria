package com.example.data

data class Chapter(
    val id: Int,
    val title: String,
    val paragraphs: List<String>
)

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val category: String,
    val year: String,
    val synopsis: String,
    val chapters: List<Chapter>,
    val coverBg: Long, // Hex color representing the cover
    val coverEmoji: String, // Icon/Emoji identifier
    val estimatedMinutes: Int
)

object BookRepository {
    val books: List<Book> = listOf(
        Book(
            id = "el_principito",
            title = "El Principito",
            author = "Antoine de Saint-Exupéry",
            category = "Fantasía Filosófica",
            year = "1943",
            synopsis = "Un piloto perdido en el desierto del Sahara se encuentra con un pequeño príncipe que viene de otro planeta. A través de sus conversaciones, la obra reflexiona de manera poética e inolvidable sobre el amor, la amistad y lo absurdo del mundo de los adultos.",
            coverBg = 0xFF1E293B, // Midnight Slate Blue
            coverEmoji = "🌹",
            estimatedMinutes = 25,
            chapters = listOf(
                Chapter(
                    id = 1,
                    title = "Capítulo I: El Encuentro en el Desierto",
                    paragraphs = listOf(
                        "Viví así, solo, sin nadie con quien hablar verdaderamente, hasta que tuve una avería en el desierto del Sahara, hace seis años. Algo se había roto en mi motor. Y como no llevaba conmigo ni mecánico ni pasajeros, me dispuse a realizar, solo, una difícil reparación. Era para mí una cuestión de vida o muerte. Tenía agua de beber apenas para ocho días.",
                        "La primera noche me duermo sobre la arena, a mil millas de toda tierra habitada. Estaba más aislado que un náufrago en una balsa en medio del océano. Imaginen, pues, mi sorpresa cuando, al romper el día, me despertó una extraña vocecita que decía: —Por favor... ¡dibújame un cordero!",
                        "Me puse de pie de un salto, como golpeado por el rayo. Me froté bien los ojos. Miré con atención. Y vi a un hombrecito extraordinario de mirada profunda que me observaba gravemente. No parecía perdido, ni muerto de cansancio, ni muerto de hambre, ni muerto de sed, ni muerto de miedo."
                    )
                ),
                Chapter(
                    id = 2,
                    title = "Capítulo II: La Misteriosa Flor",
                    paragraphs = listOf(
                        "Aprendí rápidamente a conocer mejor esa flor. Siempre había habido en el planeta del principito flores muy simples, adornadas con una sola hilera de pétalos, que casi no ocupaban espacio y no molestaban a nadie. Aparecían una mañana entre la hierba y luego se apagaban por la noche.",
                        "Pero aquella flor había germinado un día de una semilla traída de no se sabe dónde, y el principito había vigilado muy de cerca esa ramita que no se parecía a las otras. Podía ser un nuevo tipo de baobab. Pero el arbusto cesó pronto de crecer y comenzó a preparar una flor.",
                        "El principito, que asistió a la formación de un capullo enorme, sentía que de allí saldría una aparición milagrosa, pero la flor no terminaba de preparar su belleza al abrigo de su cámara verde. Elegía sus colores con cuidado. Se vestía lentamente, ajustaba sus pétalos uno a uno. ¡No quería salir arrugada como las amapolas! Ella quería aparecer con el pleno resplandor de su belleza."
                    )
                ),
                Chapter(
                    id = 3,
                    title = "Capítulo III: El Secreto de la Vida",
                    paragraphs = listOf(
                        "Fue entonces cuando apareció el zorro de pelaje anaranjado y orejas largas. —Buenos días —dijo el zorro gravemente debajo de un árbol frondoso.",
                        "—Buenos días —respondió cortésmente el principito, que se dio vuelta pero no vio nada en los alrededores.",
                        "—Estoy aquí —dijo la voz—, bajo el manzano florecido. —Párate a jugar conmigo —le propuso el principito—. Estoy tan triste...",
                        "—No puedo jugar contigo —dijo el zorro—. No estoy domesticado. —Ah, perdón —dijo el principito. Pero, después de reflexionar, agregó: —¿Qué significa 'domesticar'?",
                        "—Es una cosa ya muy olvidada —dijo el zorro—. Significa 'crear lazos'... Para mí, todavía no eres más que un muchachito semejante a cien mil muchachitos. Y no te necesito. Y tú tampoco me necesitas. Pero, si me domesticas, nos necesitaremos el uno al otro. Serás para mí único en el mundo. Seré para ti único en el mundo...",
                        "—Mi vida es muy monótona —continuó el zorro—. Pero si me domesticas, mi vida se llenará de sol. Conoceré un ruido de pasos que será diferente de todos los otros. Los otros pasos me hacen esconder bajo la tierra. El tuyo me llamará fuera de la madriguera, como una música divina.",
                        "Y luego agregó su gran secreto: —He aquí mi secreto. Es muy simple: solo con el corazón se puede ver bien; lo esencial es invisible para los ojos. Recuerda siempre esto: el tiempo que perdiste por tu rosa hace que tu rosa sea tan importante."
                    )
                )
            )
        ),
        Book(
            id = "don_quijote",
            title = "Don Quijote de la Mancha",
            author = "Miguel de Cervantes",
            category = "Literatura Clásica",
            year = "1605",
            synopsis = "La genial novela que narra las aventuras de Alonso Quijano, un hidalgo empobrecido que, de tanto leer libros de caballerías, pierde el juicio y decide convertirse en caballero andante con el nombre de Don Quijote de la Mancha, recorriendo los campos españoles para defender la justicia con su fiel escudero Sancho Panza.",
            coverBg = 0xFF5C4033, // Warm vintage bronze brown
            coverEmoji = "🛡️",
            estimatedMinutes = 30,
            chapters = listOf(
                Chapter(
                    id = 1,
                    title = "Capítulo I: De la Condición y Ejercicios del Hidalgo",
                    paragraphs = listOf(
                        "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor. Una olla de algo más vaca que carnero, salpicón las más noches, duelos y quebrantos los sábados, lantejas los viernes, algún palomino de añadidura los domingos, consumían las tres partes de su hacienda.",
                        "Tenía en su casa una ama que pasaba de los cuarenta, y una sobrina que no llegaba a los veinte, y un mozo de campo y plaza, que así ensillaba el rocín como tomaba la podadera. Frisaba la edad de nuestro hidalgo con los cincuenta años; era de complexión recia, seco de carnes, enjuto de rostro, gran madrugador y amigo de la caza.",
                        "Es, pues, de saber que este sobredicho hidalgo, los ratos que estaba ocioso, que eran los más del año, se daba a leer libros de caballerías, con tanta afición y gusto, que olvidó casi de todo punto el ejercicio de la caza, y aun la administración de su hacienda. Y llegó a tanto su curiosidad y desatino en esto, que vendió muchas hanegas de tierra de sembradura para comprar libros de caballerías en que leer, y así, trajo a su casa todos cuantos pudo haber de ellos."
                    )
                ),
                Chapter(
                    id = 2,
                    title = "Capítulo II: La Aventura de los Molinos de Viento",
                    paragraphs = listOf(
                        "En esto, descubrieron treinta o cuarenta molinos de viento que hay en aquel campo, y así como don Quijote los vio, dijo a su escudero: —La ventura va guiando nuestras cosas mejor de lo que acertáramos a desear; porque ves allí, amigo Sancho Panza, donde se descubren treinta o pocos más desaforados gigantes, con quien pienso hacer batalla y quitarles a todos las vidas, con cuyos despojos comenzaremos a enriquecer; que esta es buena guerra, y es gran servicio de Dios quitar tan mala simiente de sobre la faz de la tierra.",
                        "—¿Qué gigantes? —dijo Sancho Panza con sus ojos muy abiertos de asombro.",
                        "—Aquellos que allí ves —respondió su amo—, de los brazos largos, que los suelen tener algunos de casi dos leguas.",
                        "—Mire vuestra merced —respondió Sancho— que aquellos que allí se parecen no son gigantes, sino molinos de viento, y lo que en ellos parecen brazos son las aspas, que volteadas del viento hacen andar la piedra del molino.",
                        "—Bien se parece —respondió don Quijote— que no estás cursado en esto de las aventuras; ellos son gigantes; y si tienes miedo, quítate de ahí, y ponte en oración en el espacio que yo voy a entrar con ellos en fiera y desigual batalla.",
                        "Y diciendo esto, dio de espuelas a su caballo Rocinante, sin atender a las voces que su escudero Sancho le daba, advirtiéndole que sin duda alguna eran molinos de viento y no gigantes aquellos que iba a acometer. Pero él iba tan puesto en que eran gigantes, que ni oía las voces de Sancho, ni echaba de ver, aunque estaba ya bien cerca, lo que eran."
                    )
                )
            )
        ),
        Book(
            id = "la_metamorfosis",
            title = "La Metamorfosis",
            author = "Franz Kafka",
            category = "Existencialismo",
            year = "1915",
            synopsis = "Una mañana, Gregorio Samsa despierta en su habitación para encontrarse transformado en un monstruoso insecto gigante. La novela sigue su trágica deshumanización y el impacto devastador que esta extraña condición provoca en su familia y en su vida personal.",
            coverBg = 0xFF14532D, // Dark Forest Crimson-Green
            coverEmoji = "🪲",
            estimatedMinutes = 20,
            chapters = listOf(
                Chapter(
                    id = 1,
                    title = "Capítulo I: El Despertar de Gregorio Samsa",
                    paragraphs = listOf(
                        "Al despertar Gregorio Samsa una mañana, tras un sueño intranquilo, se encontró en su cama convertido en un monstruoso bicho. Estaba echado sobre el quitinoso caparazón de su espalda, dura como una coraza, y al levantar un poco la cabeza vio su vientre arqueado, parduzco, dividido en partes arqueadas.",
                        "La sábana apenas podía mantenerse en su posición y estaba a punto de escurrirse por completo. Sus numerosas patas, lamentablemente delgadas en comparación con el grosor ordinario de sus piernas, centelleaban desamparadas ante sus ojos de manera deprimente.",
                        "—¿Qué me ha ocurrido? —pensó de inmediato. No era un sueño en absoluto. Su habitación, una verdadera habitación humana, aunque algo pequeña, permanecía tranquila entre las cuatro paredes harto conocidas. Un cuadro que representaba a una dama con un sombrero de piel y una boa de plumas adornaba la pared contraria.",
                        "La mirada de Gregorio se dirigió entonces hacia la ventana, y el tiempo lluvioso —se oían caer las gotas de agua sobre el alféizar de metal— le puso muy melancólico. «¿Qué pasaría si durmiese un poco más y me olvidase de todas estas locuras?», pensó, pero esto era del todo imposible, porque estaba acostumbrado a dormir del lado derecho y en su estado actual no podía adoptar esa posición."
                    )
                ),
                Chapter(
                    id = 2,
                    title = "Capítulo II: La Reacción Familiar",
                    paragraphs = listOf(
                        "No se abrió la puerta hasta la noche tardía. Gregorio comprendió con satisfacción, por el cuchicheo en el pasillo, que su familia no quería dejarle solo bajo ningún concepto en esas horas de angustia, pero que tampoco tenían un valor suficiente para entrar a verle.",
                        "De pronto, se oyó ruido de llaves desde el exterior y la puerta se entreabrió lentamente. Su hermana Grete introdujo su cabeza con extrema cautela. Al ver a Gregorio trepado en la pared, contuvo un suspiro de terror y cerró apresuradamente, aunque volvió a abrir un instante después al ver que él no se movía.",
                        "Grete entró de puntillas cargando un cuenco de madera lleno de leche azucarada fresca donde flotaban pedazos de pan blanco. El olor del alimento complació enormemente a Gregorio, quien avanzó con avidez. Pero al probar la leche, descubrió con horror que ya no le agradaba en absoluto el sabor humano, prefiriendo en cambio cáscaras de verduras viejas y quesos fermentados."
                    )
                )
            )
        ),
        Book(
            id = "dracula",
            title = "Drácula",
            author = "Bram Stoker",
            category = "Terror Gótico",
            year = "1897",
            synopsis = "Jonathan Harker, un joven abogado inglés, viaja al remoto castillo del Conde Drácula en Transilvania para asesorarle en una compra inmobiliaria en Londres. Pronto descubre que se encuentra atrapado por un ser demoníaco con sed ilimitada de sangre humana.",
            coverBg = 0xFF450A0A, // Deep Gothic Sangria Red
            coverEmoji = "🦇",
            estimatedMinutes = 35,
            chapters = listOf(
                Chapter(
                    id = 1,
                    title = "Capítulo I: El Viaje a Transilvania",
                    paragraphs = listOf(
                        "Mi viaje comenzó en la estación de Múnich a las ocho de la noche, y llegué a Viena temprano a la mañana siguiente. El paisaje que cruzamos a lo largo de las llanuras húngaras era de una belleza extraña, colmado de castillos medievales en ruinas y densos bosques oscuros que se mecían al paso de los vientos del este.",
                        "Al anochecer llegué al desfiladero de Borgo, un paso de montaña que divide las tierras conocidas del misterioso territorio transilvano. El aire era tan frío que congelaba el aliento, y las montañas circundantes se elevaban como monstruos encapuchados que vigilaban el camino de piedra.",
                        "Un carruaje negro tirado por cuatro caballos oscuros apareció de repente de entre las sombras del bosque. El cochero, un hombre de hombros anchos cubierto por una gran capa de lana negra y sombrero bajo que ocultaba su rostro, me hizo una seña en silencio para que subiera. Sus ojos parecían arder como carbones encendidos."
                    )
                ),
                Chapter(
                    id = 2,
                    title = "Capítulo II: El Encuentro con el Conde",
                    paragraphs = listOf(
                        "El carruaje se detuvo finalmente en el patio de un gran castillo en ruinas, cuyas altas torres se recortaban como agujas sombrías contra el cielo estrellado. El cochero descendió y, con un gesto rápido y fuerte, abrió la pesada puerta de hierro cubierto de herrumbre.",
                        "Estaba yo temblando en el umbral cuando una luz apareció en el interior. Un anciano alto, de largo bigote blanco y vestido de negro de la cabeza a los pies, avanzó sosteniendo un candelabro de plata. Su porte era aristocrático y sus manos estaban extremadamente frías al estrechar la mía.",
                        "—Bienvenido a mi casa —dijo con una voz profunda, lenta y un acento marcado—. Entre libremente y por su propia voluntad, señor Harker. Su piel era de un color pálido casi fantasmal, y noté con un escalofrío que, cuando sonrió revelando unos colmillos inusualmente afilados, sus ojos brillaron con una luz roja siniestra."
                    )
                )
            )
        ),
        Book(
            id = "alicia_maravillas",
            title = "Alicia en el País de las Maravillas",
            author = "Lewis Carroll",
            category = "Fantasía Absurda",
            year = "1865",
            synopsis = "Alicia persigue a un inquieto conejo blanco que viste chaleco y corre consultando su reloj de bolsillo. Tras saltar tras él por una gran madriguera, se sumerge en el asombroso, absurdo y disparatado País de las Maravillas.",
            coverBg = 0xFF0F766E, // Mystic Teal
            coverEmoji = "🐇",
            estimatedMinutes = 20,
            chapters = listOf(
                Chapter(
                    id = 1,
                    title = "Capítulo I: Abajo por la Madriguera",
                    paragraphs = listOf(
                        "Alicia empezaba ya a cansarse de estar sentada con su hermana a la orilla del río, sin tener nada que hacer. Había echado un par de miradas al libro que su hermana estaba leyendo, pero no tenía dibujos ni diálogos. «¿Y de qué sirve un libro sin dibujos ni diálogos?», se preguntaba Alicia.",
                        "De pronto, un Conejo Blanco de ojos rosados pasó corriendo cerca de ella. No había nada de extraordinario en aquello, pero cuando el Conejo sacó un reloj de bolsillo de su chaleco, lo miró con prisa y exclamó: —¡Dios mío! ¡Llegaré tarde!, Alicia se puso de pie de un salto.",
                        "Nunca antes había visto a un conejo con chaleco ni con reloj de bolsillo. Ardiendo de curiosidad, corrió tras él por el campo y llegó justo a tiempo para ver cómo se introducía en una gran madriguera que se abría bajo un arbusto. Sin pensarlo dos veces, Alicia saltó tras él sin dudarlo.",
                        "La madriguera avanzaba horizontalmente como un túnel durante un trecho, y luego se hundía de golpe, tan repentinamente que Alicia no tuvo tiempo de detenerse antes de encontrarse cayendo por un profundísimo pozo que parecía no tener fin. ¿Caería tanto que terminaría atravesando la tierra para salir donde la gente camina cabeza abajo?"
                    )
                ),
                Chapter(
                    id = 2,
                    title = "Capítulo II: La Merienda de Locos",
                    paragraphs = listOf(
                        "Había una mesa puesta debajo de un árbol, delante de la casa, y la Liebre de Marzo y el Sombrerero estaban tomando el té en ella. Un Lirón estaba sentado pacíficamente en medio de ellos, durmiendo profundamente, y los otros dos lo usaban como cojín instalando sus codos sobre él.",
                        "«Muy incómodo para el Lirón», pensó Alicia; «pero como está dormido, supongo que no le importa de ninguna manera». La mesa era muy grande, pero los tres se apiñaban de forma ridícula en un solo extremo. —¡No hay sitio! ¡No hay sitio! —gritaron al ver que Alicia se acercaba.",
                        "—¡Hay muchísimo sitio! —dijo Alicia indignada, y se sentó en un gran sillón cómodo en uno de los extremos libres de la mesa. La liebre de Marzo sonrió de oreja a oreja y le ofreció: —¿Quieres un poco de vino?, Alicia miró por toda la mesa pero no vio nada de vino. —No veo ningún vino —observó Alicia. —No lo hay —dijo la Liebre de Marzo."
                    )
                )
            )
        )
    )

    fun getBooksForLanguage(lang: String): List<Book> {
        if (lang == "ES") return books
        return books.map { book ->
            when (lang) {
                "EN" -> translateToEnglish(book)
                "FR" -> translateToFrench(book)
                "PT" -> translateToPortuguese(book)
                else -> book
            }
        }
    }

    private fun translateToEnglish(book: Book): Book {
        return when (book.id) {
            "el_principito" -> book.copy(
                title = "The Little Prince",
                category = "Philosophical Fantasy",
                synopsis = "A pilot lost in the Sahara Desert meets a small prince from another planet. They talk about love, friendship, and the adult world.",
                chapters = listOf(
                    Chapter(1, "Chapter I: Encounter in the Desert", listOf(
                        "I lived my life alone, without anyone to talk to, until I had an accident with my plane in the Sahara Desert six years ago. Something was broken in my engine.",
                        "The first night I went to sleep on the sand, a thousand miles from any inhabited land. Imagine my surprise when a strange voice woke me saying: 'Please... draw me a sheep!'"
                    )),
                    Chapter(2, "Chapter II: The Mysterious Flower", listOf(
                        "I soon learned to know this flower better on the prince's planet. It was very simple, with a single row of petals, taking up no space and disturbing no one.",
                        "But this flower had germinated from a seed brought from who knows where, and the prince watched it closely. It began to slow down its growth and prepare to blossom."
                    )),
                    Chapter(3, "Chapter III: The Secret of Life", listOf(
                        "It was then that the fox appeared under the apple tree. 'Good morning,' said the fox gravely. 'Who are you?' asked the prince.",
                        "And then he added his great secret: 'Here is my secret. It is very simple: It is only with the heart that one can see rightly; what is essential is invisible to the eye.'"
                    ))
                )
            )
            "don_quijote" -> book.copy(
                title = "Don Quixote",
                category = "Classic Literature",
                synopsis = "Alonso Quijano reads too many books of chivalry, loses his mind, and decides to become a knight-errant named Don Quixote.",
                chapters = listOf(
                    Chapter(1, "Chapter I: The Famous Gentleman", listOf(
                        "In a village of La Mancha, there lived a gentleman who kept a lance, an old shield, a lean horse, and a hunting dog.",
                        "This gentleman spent his idle time reading books of chivalry with such pleasure that he almost forgot about hunting or managing his estate."
                    )),
                    Chapter(2, "Chapter II: The Adventure of the Windmills", listOf(
                        "At this point they came in sight of thirty or forty windmills. Don Quixote said to Sancho Panza: 'Look there, friend Sancho, thirty monstrous giants!'",
                        "'What giants?' asked Sancho with wide eyes. 'Those are windmills, and their arms are sails moved by the wind!'"
                    ))
                )
            )
            "la_metamorfosis" -> book.copy(
                title = "The Metamorphosis",
                category = "Existentialism",
                synopsis = "Gregor Samsa wakes up to find himself transformed into a giant insect, leading to tragedy for his family.",
                chapters = listOf(
                    Chapter(1, "Chapter I: The Awakening", listOf(
                        "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a monstrous insect.",
                        "His room, a proper human room although a little small, lay peacefully between its four familiar walls. 'What has happened to me?' he thought."
                    )),
                    Chapter(2, "Chapter II: The Family's Reaction", listOf(
                        "The door remained closed until late. Gregor understood from the whispers that his family did not want to leave him alone, but was afraid to enter.",
                        "His sister Grete entered cautiously bringing sweet milk with bread. Gregor soon discovered he preferred rotten vegetables over fresh human food."
                    ))
                )
            )
            "dracula" -> book.copy(
                title = "Dracula",
                category = "Gothic Horror",
                synopsis = "Jonathan Harker travels to Transylvania to meet Count Dracula, only to find himself trapped in a gothic horror.",
                chapters = listOf(
                    Chapter(1, "Chapter I: Journey to Transylvania", listOf(
                        "My journey began in Munich, and I arrived to the Borgo Pass at sunset. The mountains rose like hooded giants watching the road.",
                        "A black carriage appeared from the shadows. The coachman, covered in a big black cloak, gestured silently for me to climb up. His eyes burned like coals."
                    )),
                    Chapter(2, "Chapter II: Meeting the Count", listOf(
                        "The carriage stopped at a vast ruined castle. A tall old man in black, with a long white moustache, held a silver candlestick.",
                        "'Welcome to my house,' he said in a deep voice. 'Enter freely, Mr. Harker.' His hands were extremely cold, and his eyes had a red gleam."
                    ))
                )
            )
            "alicia_maravillas" -> book.copy(
                title = "Alice in Wonderland",
                category = "Absurd Fantasy",
                synopsis = "Alice follows a white rabbit down a rabbit hole into a bizarre and nonsense world.",
                chapters = listOf(
                    Chapter(1, "Chapter I: Down the Rabbit Hole", listOf(
                        "Alice was getting tired of sitting by her sister on the river bank, with nothing to do, when a White Rabbit with pink eyes ran past her.",
                        "The Rabbit took a watch out of its waistcoat pocket, and Alice ran after it, jumping down a large rabbit hole under the hedge without thinking."
                    )),
                    Chapter(2, "Chapter II: A Mad Tea-Party", listOf(
                        "There was a table set under a tree, where the March Hare and the Hatter were having tea. A Dormouse sat asleep between them.",
                        "'No room! No room!' they cried out as Alice approached. 'There's plenty of room!' said Alice indignantly, sitting down."
                    ))
                )
            )
            else -> book
        }
    }

    private fun translateToFrench(book: Book): Book {
        return when (book.id) {
            "el_principito" -> book.copy(
                title = "Le Petit Prince",
                category = "Fantaisie Philosophique",
                synopsis = "Un pilote perdu dans le désert du Sahara rencontre un petit prince venu d'une autre planète. Ils parlent de l'amour, de l'amitié et du monde.",
                chapters = listOf(
                    Chapter(1, "Chapitre I: Rencontre dans le Désert", listOf(
                        "J'ai ainsi vécu seul, sans personne avec qui parler, jusqu'à une panne dans le désert du Sahara, il y a six ans. Quelque chose s'était cassé dans mon moteur.",
                        "Le premier soir, je me suis endormi sur le sable à mille milles de toute terre habitée. Étonné lorsque, au lever du jour, une voix m'a réveillé : 'S'il vous plaît... dessine-moi un mouton !'"
                    )),
                    Chapter(2, "Chapitre II: La Fleur Mystérieuse", listOf(
                        "J'appris vite à connaître cette fleur sur la planète. Il y avait toujours eu des fleurs très simples, ornées d'un seul rang de pétales.",
                        "Mais celle-là avait germé d'une graine apportée d'on ne sait où, et le petit prince avait surveillé de près cette brindille."
                    )),
                    Chapter(3, "Chapitre III: Le Secret de la Vie", listOf(
                        "C'est alors qu'apparut le renard. 'Bonjour', dit le renard sous le pommier. 'Qui es-tu ?' d'emanda le petit prince.",
                        "Et puis il ajouta son grand secret: 'Voici mon secret. Il est très simple: on ne voit bien qu'avec le cœur. L'essentiel est invisible pour les yeux.'"
                    ))
                )
            )
            "don_quijote" -> book.copy(
                title = "Don Quichotte",
                category = "Littérature Classique",
                synopsis = "Alonso Quijano perd la raison à force de lire des livres de chevalerie et décide de devenir le chevalier errant Don Quichotte.",
                chapters = listOf(
                    Chapter(1, "Chapitre I: Le Fameux Hidalgo", listOf(
                        "Dans un village de la Manche vivait un hidalgo qui avait une lance, un vieux bouclier, un cheval maigre et un chien de chasse.",
                        "Cet hidalgo passait son temps libre à lire des livres de chevalerie avec tant de passion qu'il en oublia de gérer ses biens."
                    )),
                    Chapter(2, "Chapitre II: L'Aventure des Moulins à Vent", listOf(
                        "Sur ces entrefaites, ils découvrirent trente ou quarante moulins à vent. Don Quichotte dit à Sancho : 'Regarde là, trente géants démesurés !'",
                        "« Quels géants ? », demanda Sancho. « Ce sont des moulins, et ce qui semble être des bras sont les ailes tournées par le vent ! »"
                    ))
                )
            )
            "la_metamorfosis" -> book.copy(
                title = "La Métamorphose",
                category = "Existentialisme",
                synopsis = "Grégoire Samsa se réveille transformé en un monstrueux insecte géant, provoquant un drame familial.",
                chapters = listOf(
                    Chapter(1, "Chapitre I: Le Réveil", listOf(
                        "Un matin, Grégoire Samsa s'éveilla transformé dans son lit en une véritable vermine.",
                        "Sa chambre, une vraie chambre humaine, bien qu'un peu petite, était paisible entre ses quatre murs. « Qu'est-ce qui m'est arrivé ? », pensa-t-il."
                    )),
                    Chapter(2, "Chapitre II: La Réaction Familiale", listOf(
                        "La porte ne s'ouvrit que très tard. Grégoire comprit que sa famille ne voulait pas le laisser seul, mais avait peur d'entrer.",
                        "Sa sœur Grete entra en apportant du lait frais et du pain. Grégoire découvrit bientôt qu'il préférait les légumes pourris à la nourriture humaine."
                    ))
                )
            )
            "dracula" -> book.copy(
                title = "Dracula",
                category = "Horreur Gothique",
                synopsis = "Jonathan Harker voyage en Transylvanie pour rencontrer le mystérieux Comte Dracula et se retrouve piégé dans son château.",
                chapters = listOf(
                    Chapter(1, "Chapitre I: Le Voyage en Transylvanie", listOf(
                        "Mon voyage commença à Munich et j'arrivai au col de Borgo au coucher du soleil. Les montagnes s'élevaient comme des géants encapuchonnés.",
                        "Une calèche noire parut de la forêt. Le cocher enveloppé d'un manteau noir me fit signe de monter. Ses yeux brillaient comme des braises."
                    )),
                    Chapter(2, "Chapitre II: Rencontre avec le Comte", listOf(
                        "La calèche s'arrêta dans un grand château en ruine. Un vieillard vêtu de noir, tenant un chandelier d'argent, s'avança.",
                        "« Bienvenue chez moi ! », dit-il d'une voix grave. « Entrez librement, Monsieur Harker. » Ses mains étaient glaciales."
                    ))
                )
            )
            "alicia_maravillas" -> book.copy(
                title = "Alice au Pays des Merveilles",
                category = "Fantaisie Absurde",
                synopsis = "Alice poursuit un Lapin Blanc et plonge dans l'univers loufoque et fantastique du Pays des Merveilles.",
                chapters = listOf(
                    Chapter(1, "Chapitre I: Au Fond du Terrier", listOf(
                        "Alice commençait à se fatiguer d'être assise à côté de sa sœur au bord de l'eau, quand un Lapin Blanc aux yeux roses passa près d'elle.",
                        "Le Lapin tira une montre de son gilet, et Alice courut à sa suite avant de sauter dans un grand terrier sous la haie sans hésiter."
                    )),
                    Chapter(2, "Chapitre II: Un Thé chez les Fous", listOf(
                        "Une table était dressée sous un arbre, où le Lièvre de Mars et le Chapelier prenaient le thé. Un Loir dormait entre eux.",
                        "« Pas de place ! », crièrent-ils quand ils virent venir Alice. « Il y a de la place de reste ! », dit Alice indignée en s'asseyant."
                    ))
                )
            )
            else -> book
        }
    }

    private fun translateToPortuguese(book: Book): Book {
        return when (book.id) {
            "el_principito" -> book.copy(
                title = "O Pequeno Príncipe",
                category = "Fantasia Filosófica",
                synopsis = "Um piloto perdido no deserto do Saara encontra um pequeno príncipe vindo de outro planeta. Eles conversam sobre o amor, a amizade e o mundo.",
                chapters = listOf(
                    Chapter(1, "Capítulo I: O Encontro no Deserto", listOf(
                        "Vivi assim, só, sem ninguém com quem falar de verdade, até que tive uma pane no deserto do Saara, há seis anos. Algo se quebrara no motor.",
                        "Na primeira noite dormi sobre a areia. Fui acordado ao amanhecer por uma estranha vozinha que dizia: 'Por favor... desenha-me um carneiro!'"
                    )),
                    Chapter(2, "Capítulo II: A Flor Misteriosa", listOf(
                        "Aprendi a conhecer melhor essa flor no planeta do pequeno príncipe. Era simples, com uma só fileira de pétalas, sem incomodar ninguém.",
                        "But aquela flor havia germinado de uma semente trazida sutilmente, e o pequeno príncipe vigiara de perto aquele raminho."
                    )),
                    Chapter(3, "Capítulo III: O Segredo da Vida", listOf(
                        "Foi então que apareceu a raposa. 'Bom dia', disse a raposa sob a macieira. 'Quem é você?' perguntou o pequeno príncipe.",
                        "E depois ela acrescentou o segredo: 'Eis o meu segredo. É muito simples: só se vê bem com o coração; o essencial é invisível aos olhos.'"
                    ))
                )
            )
            "don_quijote" -> book.copy(
                title = "Dom Quixote",
                category = "Literatura Clássica",
                synopsis = "Alonso Quijano perde o juízo de tanto ler romances de cavalaria e decide tornar-se o cavaleiro andante Dom Quixote.",
                chapters = listOf(
                    Chapter(1, "Capítulo I: O Famoso Fidalgo", listOf(
                        "Num lugar da Mancha vivia um fidalgo dos de lança em cabido, adaga antiga, rocinante magro e galgo corredor.",
                        "Este fidalgo dava em ler livros de cavalaria com tanta afeição que esqueceu quase de todo o exercício da caça ou seus bens."
                    )),
                    Chapter(2, "Capítulo II: A Aventura dos Moinhos de Vento", listOf(
                        "Nisto, descobriram trinta ou quarenta moinhos de vento. Dom Quixote disse ao seu escudeiro: 'Vês ali, amigo Sancho, trinta descomunais gigantes?'",
                        "«Que gigantes?», disse Sancho Pança. «Aqueles são moinhos de vento, e o que parecem braços são as pás movidas pelo vento!»"
                    ))
                )
            )
            "la_metamorfosis" -> book.copy(
                title = "A Metamorfose",
                category = "Existencialismo",
                synopsis = "Gregor Samsa acorda transformado em um inseto monstruoso, causando um grande impacto devastador em sua família.",
                chapters = listOf(
                    Chapter(1, "Capítulo I: O Despertar", listOf(
                        "Quando Gregor Samsa acordou certa manhã de sonhos intranquilos, encontrou-se metamorfoseado num inseto monstruoso.",
                        "Seu quarto permanecia calmo entre as quatro paredes bem conhecidas. «O que aconteceu comigo?», pensou ele."
                    )),
                    Chapter(2, "Capítulo II: A Reação da Família", listOf(
                        "A porta só se abriu à noite. Gregor compreendeu que sua família não queria deixá-lo sozinho, mas ninguém tinha coragem de entrar.",
                        "Sua irmã Grete entrou com leite doce e pedaços de pão. Gregor descobriu que preferia cascas de legumes do que comida fresca."
                    ))
                )
            )
            "dracula" -> book.copy(
                title = "Drácula",
                category = "Terror Gótico",
                synopsis = "Jonathan Harker viaja ao castelo do Conde Drácula na Transilvânia e descobre que está preso sob um terror sombrio.",
                chapters = listOf(
                    Chapter(1, "Capítulo I: A Viagem para a Transilvânia", listOf(
                        "Minha viagem começou em Munique, e cheguei ao desfiladeiro de Borgo no anoitecer. As montanhas erguiam-se como gigantes encapuzados.",
                        "Uma carruagem negra apareceu de repente. O cocheiro, coberto por uma grande capa preta, acenou para que eu subisse."
                    )),
                    Chapter(2, "Capítulo II: O Encontro com o Conde", listOf(
                        "A carruagem parou no pátio de um castelo em ruínas. Um ancião alto, de longo bigode branco e vestido de preto, avançou.",
                        "«Bem-vindo à minha casa», disse com voz profunda. «Entre livremente, senhor Harker.» Suas mãos estavam extremamente frias."
                    ))
                )
            )
            "alicia_maravillas" -> book.copy(
                title = "Alice no País das Maravilhas",
                category = "Fantasia Absurda",
                synopsis = "Alice persegue um Coelho Branco vestindo colete e mergulha no assombroso País das Maravilhas.",
                chapters = listOf(
                    Chapter(1, "Capítulo I: Descendo pela Toca", listOf(
                        "Alice começava a estar cansada de estar sentada com sua irmã na beira do rio, quando um Coelho Branco de olhos cor-de-rosa passou correndo.",
                        "O coelho tirou um relógio do colete, e Alice correu atrás dele pelo campo, saltando dentro de uma grande toca de coelho sem hesitar."
                    )),
                    Chapter(2, "Capítulo II: O Chá de Loucos", listOf(
                        "Havia uma mesa posta debaixo de uma árvore, onde a Lebre de Março e o Chapeleiro estavam tomando chá com um Leirão no meio.",
                        "«Não há lugar!», gritaram quando viram Alice. «Há muitíssimo lugar!», disse Alice indignada, sentando-se num grande cadeirão."
                    ))
                )
            )
            else -> book
        }
    }
}
