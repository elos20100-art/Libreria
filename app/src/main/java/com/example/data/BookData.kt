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
}
