const subjects = [
  {
    id: "calidad",
    name: "Topicos de calidad",
    color: "#265c9b",
    topics: [
      {
        title: "Calidad de software",
        detail: "Aprende a reconocer cuando un programa no solo funciona, sino que tambien es confiable, facil de usar, seguro y sencillo de mantener. Debes poder explicar atributos como funcionalidad, usabilidad, rendimiento, mantenibilidad y seguridad con ejemplos reales."
      },
      {
        title: "Principios SOLID, DRY y KISS",
        detail: "Estudia como organizar el codigo para evitar clases gigantes, duplicacion innecesaria y soluciones complicadas. La meta es que sepas detectar responsabilidades mezcladas y proponer una estructura mas limpia."
      },
      {
        title: "Patrones de diseno basicos",
        detail: "Comprende que los patrones son soluciones conocidas para problemas comunes. Enfocate en identificar cuando usar Factory, Singleton, Strategy u Observer sin memorizar nombres vacios."
      },
      {
        title: "Pruebas de software",
        detail: "Aprende la diferencia entre pruebas unitarias, de integracion y de aceptacion. Debes practicar casos normales, casos limite y casos con errores para comprobar que el programa responde bien."
      },
      {
        title: "Metricas y revisiones de codigo",
        detail: "Conoce metricas simples como complejidad, cobertura, duplicacion y cantidad de errores. Practica revisar codigo con preguntas: se entiende, se puede cambiar, se puede probar y se puede reutilizar."
      },
      {
        title: "Arquitectura limpia",
        detail: "Aprende a separar interfaz, logica de negocio y datos. Aunque el proyecto sea pequeno, debes poder evitar que todo quede mezclado en un solo archivo o una sola clase."
      }
    ],
    practice: [
      ["Checklist de calidad", "Evalua una app que uses y lista 5 mejoras concretas."],
      ["Refactor pequeno", "Toma una funcion larga y separala en responsabilidades claras."],
      ["Pruebas", "Escribe casos de prueba para validar entradas correctas e incorrectas."]
    ]
  },
  {
    id: "bd",
    name: "Bases de datos",
    color: "#17786a",
    topics: [
      {
        title: "Tablas, registros y tipos de datos",
        detail: "Aprende como se organiza la informacion en tablas, filas y columnas. Debes saber elegir tipos de datos correctos para nombres, fechas, precios, calificaciones e identificadores."
      },
      {
        title: "Modelo entidad-relacion",
        detail: "Practica convertir un problema real en entidades, atributos y relaciones. La meta es que puedas dibujar un diagrama antes de crear la base de datos."
      },
      {
        title: "Llaves primarias y foraneas",
        detail: "Comprende como una llave primaria identifica registros y como una llave foranea conecta tablas. Debes dominar relaciones uno a uno, uno a muchos y muchos a muchos."
      },
      {
        title: "Normalizacion",
        detail: "Aprende a reducir datos repetidos y evitar inconsistencias. Practica llevar una tabla desordenada a primera, segunda y tercera forma normal."
      },
      {
        title: "SQL basico",
        detail: "Domina SELECT, INSERT, UPDATE y DELETE. Debes poder consultar, agregar, modificar y eliminar datos sin depender de ejemplos copiados."
      },
      {
        title: "Consultas avanzadas",
        detail: "Aprende JOIN, GROUP BY, ORDER BY, HAVING y subconsultas. Enfocate en responder preguntas utiles, como promedios, conteos, filtros y reportes."
      },
      {
        title: "Transacciones y seguridad",
        detail: "Comprende por que una base de datos debe proteger la informacion. Estudia transacciones, permisos, respaldos y errores comunes como borrar datos sin condicion."
      }
    ],
    practice: [
      ["Modelo escolar", "Disena entidades para alumnos, materias, profesores y calificaciones."],
      ["Consultas", "Resuelve 10 consultas con filtros, orden y agrupaciones."],
      ["Normalizacion", "Convierte una tabla repetida en un modelo relacional limpio."]
    ]
  },
  {
    id: "poo",
    name: "Programacion orientada a objetos",
    color: "#c7523a",
    topics: [
      {
        title: "Clases, objetos, atributos y metodos",
        detail: "Aprende a representar elementos del mundo real como objetos. Debes diferenciar entre una clase como molde, un objeto como instancia, atributos como datos y metodos como acciones."
      },
      {
        title: "Constructores y encapsulamiento",
        detail: "Estudia como inicializar objetos correctamente y proteger sus datos. Practica usar modificadores de acceso, getters, setters y validaciones simples."
      },
      {
        title: "Herencia y composicion",
        detail: "Comprende cuando una clase es un tipo de otra y cuando una clase contiene otra. La meta es evitar herencia forzada y elegir relaciones que tengan sentido."
      },
      {
        title: "Polimorfismo",
        detail: "Aprende como distintos objetos pueden responder al mismo metodo de formas diferentes. Practica sobreescritura y uso de referencias generales para codigo mas flexible."
      },
      {
        title: "Abstraccion e interfaces",
        detail: "Estudia como ocultar detalles y trabajar con contratos. Debes poder definir que debe hacer una clase sin depender de como lo hace internamente."
      },
      {
        title: "Errores y validaciones",
        detail: "Aprende a manejar datos incorrectos sin que el programa falle de forma inesperada. Practica validar entradas, lanzar errores claros y responder con mensajes utiles."
      },
      {
        title: "Diagramas UML simples",
        detail: "Usa diagramas de clases para planear antes de programar. Debes poder mostrar atributos, metodos y relaciones sin convertir el diagrama en algo excesivamente complicado."
      }
    ],
    practice: [
      ["Clases base", "Crea Alumno, Materia y Profesor con atributos y metodos."],
      ["Relaciones", "Conecta Alumno con Materia usando listas o colecciones."],
      ["Interfaces", "Define una interfaz Evaluable para calcular promedios."]
    ]
  },
  {
    id: "calculo",
    name: "Calculo integral",
    color: "#a87518",
    topics: [
      {
        title: "Derivadas y antiderivadas",
        detail: "Repasa reglas basicas de derivacion porque integrar muchas veces significa encontrar la funcion original. Debes reconocer patrones como potencias, constantes y funciones comunes."
      },
      {
        title: "Integral indefinida",
        detail: "Aprende que representa una familia de funciones y por que aparece la constante C. Practica integrales directas hasta que puedas resolverlas con seguridad."
      },
      {
        title: "Integral definida",
        detail: "Comprende la integral como acumulacion o area entre limites. Debes practicar el teorema fundamental del calculo y evaluar correctamente en limite superior e inferior."
      },
      {
        title: "Propiedades de la integral",
        detail: "Estudia linealidad, suma de integrales, constantes y cambio de limites. Estas propiedades te ayudan a simplificar ejercicios antes de resolverlos."
      },
      {
        title: "Sustitucion",
        detail: "Aprende a detectar funciones compuestas. Debes elegir una variable u, derivarla y transformar toda la integral antes de resolver."
      },
      {
        title: "Integracion por partes",
        detail: "Practica cuando la integral combina productos como polinomios con exponenciales, logaritmos o trigonometria. Aprende a elegir u y dv con criterio."
      },
      {
        title: "Fracciones parciales",
        detail: "Estudia como descomponer expresiones racionales en integrales mas simples. Debes factorizar denominadores y resolver constantes paso a paso."
      },
      {
        title: "Area bajo la curva",
        detail: "Aplica la integral definida para calcular areas, acumulacion y cambios totales. La clave es interpretar el resultado, no solo obtener un numero."
      }
    ],
    practice: [
      ["Rutina diaria", "Resuelve 5 integrales cortas y revisa cada paso."],
      ["Metodo correcto", "Clasifica ejercicios segun sustitucion, partes o fracciones parciales."],
      ["Aplicacion", "Calcula area bajo una curva sencilla y explica el resultado."]
    ]
  },
  {
    id: "ingles",
    name: "Ingles",
    color: "#3f7f42",
    topics: [
      {
        title: "Present continuous",
        detail: "Aprende la estructura subject + am/is/are + verb-ing. Usalo para acciones que estan pasando ahora y planes cercanos: I am studying, She is working, They are learning."
      },
      {
        title: "Present simple vs present continuous",
        detail: "Diferencia rutinas de acciones en progreso. Present simple describe habitos: I study every day. Present continuous describe lo que ocurre ahora: I am studying now."
      },
      {
        title: "Adverbs of frequency",
        detail: "Practica always, usually, often, sometimes, rarely y never. Debes ubicarlos correctamente y usarlos para hablar de habitos personales y academicos."
      },
      {
        title: "Past simple",
        detail: "Aprende verbos regulares e irregulares para hablar de acciones terminadas. Practica afirmaciones, negativas y preguntas con did."
      },
      {
        title: "Future with going to",
        detail: "Usa going to para planes e intenciones. Debes poder hablar de tus metas: I am going to study databases, I am going to build a project."
      },
      {
        title: "Can, should y must",
        detail: "Aprende a expresar habilidad, consejo y obligacion. Practica frases utiles para clase, estudio y presentaciones."
      },
      {
        title: "Vocabulario academico",
        detail: "Construye vocabulario sobre tecnologia, escuela, tareas, horarios y proyectos. La meta es poder explicar que estas aprendiendo con frases cortas pero claras."
      }
    ],
    practice: [
      ["5 sentences", "Write five present continuous sentences about your day."],
      ["Listening", "Listen to a short video and write 6 useful phrases."],
      ["Speaking", "Record a 1-minute explanation of what you are studying."]
    ]
  }
];

const weeklyFocus = [
  "Arranque: instala tu rutina y repasa bases.",
  "Conceptos esenciales y primeras practicas.",
  "Modela informacion y escribe codigo con objetos.",
  "Primer mini proyecto: sistema escolar en version simple.",
  "Profundiza SQL, encapsulamiento e integrales indefinidas.",
  "Relaciones, herencia y present simple vs continuous.",
  "Semana de consolidacion y primer repaso fuerte.",
  "Consultas avanzadas, polimorfismo e integrales definidas.",
  "Calidad aplicada: pruebas, documentacion y mejora de diseno.",
  "Metodos de integracion y transacciones.",
  "Proyecto integrador con base de datos y validaciones.",
  "Practica tipo examen y presentacion en ingles.",
  "Cierre de temas dificiles y correccion de debilidades.",
  "Repaso final, entrega de proyecto y simulacro de evaluacion."
];

const weeklyTasks = [
  ["Mapa de temas", "Crea una lista de temas dificiles por materia y define horarios reales."],
  ["Practica basica", "Resuelve ejercicios cortos de cada materia para detectar huecos."],
  ["Modelo escolar", "Dibuja clases y entidades para un sistema escolar mini."],
  ["Primer prototipo", "Programa clases simples y disena las primeras tablas."],
  ["SQL y antiderivadas", "Practica consultas basicas y 20 integrales indefinidas."],
  ["Comparaciones", "Usa herencia o composicion y escribe textos en presente simple/continuous."],
  ["Repaso 1", "Haz una autoevaluacion y corrige los dos temas mas debiles."],
  ["Consultas utiles", "Construye JOINs y calcula promedios con tu sistema escolar."],
  ["Calidad real", "Agrega pruebas, nombres claros y documentacion breve al proyecto."],
  ["Integrales mixtas", "Mezcla sustitucion, partes y fracciones parciales en ejercicios."],
  ["Proyecto conectado", "Une POO con base de datos y valida datos incorrectos."],
  ["Presentacion", "Explica tu proyecto en ingles con frases simples y claras."],
  ["Correcciones", "Refuerza errores frecuentes y prepara formularios de repaso."],
  ["Cierre", "Entrega version final, repasa y simula examen por materia."]
];

const challengeIdeas = [
  ["Semana 1-2", "Crea el listado de materias, alumnos y profesores en papel antes de programar."],
  ["Semana 3-4", "Construye las clases Alumno, Profesor y Materia con metodos simples."],
  ["Semana 5-6", "Disena tablas SQL para guardar alumnos, materias e inscripciones."],
  ["Semana 7-8", "Agrega consultas para ver materias inscritas y promedios."],
  ["Semana 9-10", "Agrega pruebas y una lista de criterios de calidad para tu codigo."],
  ["Semana 11-12", "Conecta el sistema a una base de datos real o simulada."],
  ["Semana 13-14", "Prepara una demo y explica en ingles que esta haciendo tu sistema."]
];

const topicResources = {
  calidad: {
    "Calidad de software": ["ISO/IEC 25010", "https://www.iso.org/standard/78176.html"],
    "Principios SOLID, DRY y KISS": ["Refactoring.Guru", "https://refactoring.guru/refactoring"],
    "Patrones de diseno basicos": ["Catalogo de patrones", "https://refactoring.guru/design-patterns"],
    "Pruebas de software": ["Atlassian Testing", "https://www.atlassian.com/continuous-delivery/software-testing/types-of-software-testing"],
    "Metricas y revisiones de codigo": ["Code Review", "https://www.atlassian.com/agile/software-development/code-reviews"],
    "Arquitectura limpia": ["Azure Architecture", "https://learn.microsoft.com/en-us/azure/well-architected/what-is-well-architected-framework"]
  },
  bd: {
    "Tablas, registros y tipos de datos": ["W3Schools SQL", "https://www.w3schools.com/sql/sql_datatypes.asp"],
    "Modelo entidad-relacion": ["Lucidchart ERD", "https://www.lucidchart.com/pages/er-diagrams"],
    "Llaves primarias y foraneas": ["W3Schools Keys", "https://www.w3schools.com/sql/sql_foreignkey.asp"],
    "Normalizacion": ["Database Normalization", "https://www.guru99.com/database-normalization.html"],
    "SQL basico": ["W3Schools SQL", "https://www.w3schools.com/sql/"],
    "Consultas avanzadas": ["PostgreSQL SELECT", "https://www.postgresql.org/docs/current/tutorial-select.html"],
    "Transacciones y seguridad": ["PostgreSQL Transactions", "https://www.postgresql.org/docs/current/tutorial-transactions.html"]
  },
  poo: {
    "Clases, objetos, atributos y metodos": ["Microsoft Classes", "https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/types/classes"],
    "Constructores y encapsulamiento": ["Microsoft OOP", "https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/tutorials/oop"],
    "Herencia y composicion": ["Microsoft Inheritance", "https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/object-oriented/inheritance"],
    "Polimorfismo": ["Microsoft Polymorphism", "https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/object-oriented/polymorphism"],
    "Abstraccion e interfaces": ["Microsoft Interfaces", "https://learn.microsoft.com/en-us/dotnet/csharp/fundamentals/types/interfaces"],
    "Errores y validaciones": ["Java Exceptions", "https://docs.oracle.com/javase/tutorial/essential/exceptions/"],
    "Diagramas UML simples": ["Lucidchart UML", "https://www.lucidchart.com/pages/uml-class-diagram"]
  },
  calculo: {
    "Derivadas y antiderivadas": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Integral indefinida": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Integral definida": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Propiedades de la integral": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Sustitucion": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Integracion por partes": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Fracciones parciales": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"],
    "Area bajo la curva": ["Khan Academy", "https://es.khanacademy.org/math/integral-calculus/ic-integration"]
  },
  ingles: {
    "Present continuous": ["British Council", "https://learnenglish.britishcouncil.org/grammar/english-grammar-reference/present-continuous"],
    "Present simple vs present continuous": ["British Council", "https://learnenglish.britishcouncil.org/grammar/english-grammar-reference/present-simple"],
    "Adverbs of frequency": ["British Council", "https://learnenglish.britishcouncil.org/grammar/a1-a2-grammar/adverbs-frequency"],
    "Past simple": ["British Council", "https://learnenglish.britishcouncil.org/grammar/english-grammar-reference/past-simple"],
    "Future with going to": ["British Council", "https://learnenglish.britishcouncil.org/grammar/a1-a2-grammar/future-plans"],
    "Can, should y must": ["British Council", "https://learnenglish.britishcouncil.org/grammar/english-grammar-reference/modal-verbs"],
    "Vocabulario academico": ["Cambridge Dictionary", "https://dictionary.cambridge.org/topics/education/"]
  }
};

const state = {
  selectedSubject: subjects[0].id,
  selectedWeek: 1,
  progress: JSON.parse(localStorage.getItem("termProgress") || "{}"),
  reviewGuides: JSON.parse(localStorage.getItem("reviewGuides") || "[]")
};

const subjectNav = document.querySelector("#subjectNav");
const weekSelect = document.querySelector("#weekSelect");
const weekPlan = document.querySelector("#weekPlan");
const weekTitle = document.querySelector("#weekTitle");
const weekFocus = document.querySelector("#weekFocus");
const topicList = document.querySelector("#topicList");
const practiceList = document.querySelector("#practiceList");
const subjectTitle = document.querySelector("#subjectTitle");
const subjectPercent = document.querySelector("#subjectPercent");
const subjectBar = document.querySelector("#subjectBar");
const overallProgress = document.querySelector("#overallProgress");
const overallBar = document.querySelector("#overallBar");
const createReviewGuide = document.querySelector("#createReviewGuide");
const reviewStatus = document.querySelector("#reviewStatus");
const savedReviewGuides = document.querySelector("#savedReviewGuides");
const reviewDialog = document.querySelector("#reviewDialog");
const reviewTitle = document.querySelector("#reviewTitle");
const reviewContent = document.querySelector("#reviewContent");

function studyLink(subjectId, topic) {
  const resource = topicResources[subjectId]?.[topic.title];
  if (!resource) {
    return "";
  }

  const [label, url] = resource;
  return `<a class="study-link" href="${url}" target="_blank" rel="noopener noreferrer">Estudiar en ${label}</a>`;
}

function saveProgress() {
  localStorage.setItem("termProgress", JSON.stringify(state.progress));
}

function saveReviewGuides() {
  localStorage.setItem("reviewGuides", JSON.stringify(state.reviewGuides));
}

function taskKey(week, subjectId) {
  return `w${week}-${subjectId}`;
}

function isDone(week, subjectId) {
  return Boolean(state.progress[taskKey(week, subjectId)]);
}

function subjectCompletion(subjectId) {
  const done = Array.from({ length: 14 }, (_, index) => isDone(index + 1, subjectId)).filter(Boolean).length;
  return Math.round((done / 14) * 100);
}

function totalCompletion() {
  const total = subjects.length * 14;
  const done = subjects.reduce((sum, subject) => {
    return sum + Array.from({ length: 14 }, (_, index) => isDone(index + 1, subject.id)).filter(Boolean).length;
  }, 0);
  return Math.round((done / total) * 100);
}

function weekCompletion(week) {
  return subjects.filter((subject) => isDone(week, subject.id)).length;
}

function isWeekComplete(week) {
  return weekCompletion(week) === subjects.length;
}

function topicForWeek(subject, week) {
  return subject.topics[(week - 1) % subject.topics.length];
}

function questionsFor(subject, topic) {
  const shared = [
    `Explica con tus palabras: ${topic.title}.`,
    "Escribe un ejemplo propio y revisa si realmente aplica el concepto."
  ];

  const bySubject = {
    calidad: "Que criterio usarias para saber si este tema mejora la calidad de un proyecto?",
    bd: "Como representarias este tema en una tabla, consulta o relacion de base de datos?",
    poo: "Que clase, metodo o relacion podrias crear para practicar este tema?",
    calculo: "Que tipo de ejercicio resolverias para comprobar que dominas este tema?",
    ingles: "Escribe 5 oraciones usando este tema y leelas en voz alta."
  };

  return [...shared, bySubject[subject.id]];
}

function buildReviewGuide(week) {
  const [taskTitle, taskText] = weeklyTasks[week - 1];
  return {
    week,
    createdAt: new Date().toISOString(),
    focus: weeklyFocus[week - 1],
    taskTitle,
    taskText,
    items: subjects.map((subject) => {
      const topic = topicForWeek(subject, week);
      return {
        subject: subject.name,
        color: subject.color,
        topic: topic.title,
        detail: topic.detail,
        questions: questionsFor(subject, topic),
        resource: topicResources[subject.id]?.[topic.title] || null
      };
    })
  };
}

function guideForWeek(week) {
  return state.reviewGuides.find((guide) => guide.week === week);
}

function renderWeeks() {
  weekSelect.innerHTML = "";
  for (let week = 1; week <= 14; week += 1) {
    const option = document.createElement("option");
    option.value = String(week);
    option.textContent = String(week);
    weekSelect.append(option);
  }
  weekSelect.value = String(state.selectedWeek);
}

function renderSubjects() {
  subjectNav.innerHTML = "";
  subjects.forEach((subject) => {
    const button = document.createElement("button");
    button.className = `subject-button ${subject.id === state.selectedSubject ? "active" : ""}`;
    button.innerHTML = `
      <span class="subject-dot" style="background:${subject.color}"></span>
      <span class="subject-name">${subject.name}</span>
      <span class="subject-score">${subjectCompletion(subject.id)}%</span>
    `;
    button.addEventListener("click", () => {
      state.selectedSubject = subject.id;
      switchView("subject");
      render();
    });
    subjectNav.append(button);
  });
}

function renderWeekPlan() {
  weekTitle.textContent = `Semana ${state.selectedWeek}`;
  weekFocus.textContent = weeklyFocus[state.selectedWeek - 1];
  weekPlan.innerHTML = "";

  subjects.forEach((subject) => {
    const card = document.createElement("article");
    card.className = "task-card";
    const checked = isDone(state.selectedWeek, subject.id);
    const topic = topicForWeek(subject, state.selectedWeek);
    const [taskTitle, taskText] = weeklyTasks[state.selectedWeek - 1];
    card.innerHTML = `
      <div>
        <p class="eyebrow" style="color:${subject.color}">${subject.name}</p>
        <strong>${topic.title}</strong>
        <p>${topic.detail}</p>
        ${studyLink(subject.id, topic)}
        <p><b>${taskTitle}:</b> ${taskText}</p>
        <label>
          <input type="checkbox" ${checked ? "checked" : ""} />
          Completado
        </label>
      </div>
    `;
    card.querySelector("input").addEventListener("change", (event) => {
      state.progress[taskKey(state.selectedWeek, subject.id)] = event.target.checked;
      saveProgress();
      render();
    });
    weekPlan.append(card);
  });
}

function renderSubjectView() {
  const subject = subjects.find((item) => item.id === state.selectedSubject);
  subjectTitle.textContent = subject.name;
  const percent = subjectCompletion(subject.id);
  subjectPercent.textContent = `${percent}%`;
  subjectBar.style.width = `${percent}%`;

  topicList.innerHTML = "";
  subject.topics.forEach((topic) => {
    const item = document.createElement("li");
    item.style.borderColor = subject.color;
    item.innerHTML = `
      <strong>${topic.title}</strong>
      <span>${topic.detail}</span>
      ${studyLink(subject.id, topic)}
    `;
    topicList.append(item);
  });

  practiceList.innerHTML = "";
  subject.practice.forEach(([title, text]) => {
    const item = document.createElement("div");
    item.className = "practice-item";
    item.style.borderColor = subject.color;
    item.innerHTML = `<strong>${title}</strong><span>${text}</span>`;
    practiceList.append(item);
  });
}

function renderReviewStatus() {
  const completed = weekCompletion(state.selectedWeek);
  const guideExists = Boolean(guideForWeek(state.selectedWeek));
  const complete = isWeekComplete(state.selectedWeek);

  createReviewGuide.disabled = !complete;
  createReviewGuide.textContent = guideExists ? "Ver guia de repaso" : "Crear guia de repaso";
  reviewStatus.textContent = complete
    ? "Semana completa: lista para repasar."
    : `Completa ${completed}/${subjects.length} materias para crear la guia.`;
}

function renderSavedReviewGuides() {
  savedReviewGuides.innerHTML = "";

  if (state.reviewGuides.length === 0) {
    savedReviewGuides.innerHTML = "<p class=\"empty-state\">Aun no has creado guias. Cuando completes una semana, podras guardarla aqui.</p>";
    return;
  }

  [...state.reviewGuides]
    .sort((a, b) => a.week - b.week)
    .forEach((guide) => {
      const button = document.createElement("button");
      button.className = "review-button";
      button.innerHTML = `
        <span>Semana ${guide.week}</span>
        <strong>${guide.focus}</strong>
      `;
      button.addEventListener("click", () => openReviewGuide(guide));
      savedReviewGuides.append(button);
    });
}

function openReviewGuide(guide) {
  reviewTitle.textContent = `Semana ${guide.week}`;
  reviewContent.innerHTML = `
    <section class="review-summary">
      <p><b>Enfoque:</b> ${guide.focus}</p>
      <p><b>Tarea clave:</b> ${guide.taskTitle}: ${guide.taskText}</p>
    </section>
    ${guide.items
      .map((item) => {
        const resource = item.resource
          ? `<a class="study-link" href="${item.resource[1]}" target="_blank" rel="noopener noreferrer">Repasar en ${item.resource[0]}</a>`
          : "";
        return `
          <article class="review-item" style="border-color:${item.color}">
            <p class="eyebrow" style="color:${item.color}">${item.subject}</p>
            <h3>${item.topic}</h3>
            <p>${item.detail}</p>
            <ul>
              ${item.questions.map((question) => `<li>${question}</li>`).join("")}
            </ul>
            ${resource}
          </article>
        `;
      })
      .join("")}
  `;
  reviewDialog.showModal();
}

function renderOverall() {
  const percent = totalCompletion();
  overallProgress.textContent = `${percent}%`;
  overallBar.style.width = `${percent}%`;
}

function render() {
  renderWeeks();
  renderSubjects();
  renderWeekPlan();
  renderSubjectView();
  renderReviewStatus();
  renderSavedReviewGuides();
  renderOverall();
}

function switchView(viewName) {
  document.querySelectorAll(".segmented-control button").forEach((button) => {
    button.classList.toggle("active", button.dataset.view === viewName);
  });
  document.querySelectorAll(".view").forEach((view) => view.classList.remove("active-view"));
  document.querySelector(`#${viewName}View`).classList.add("active-view");
}

document.querySelectorAll(".segmented-control button").forEach((button) => {
  button.addEventListener("click", () => switchView(button.dataset.view));
});

weekSelect.addEventListener("change", (event) => {
  state.selectedWeek = Number(event.target.value);
  renderWeekPlan();
  renderReviewStatus();
});

createReviewGuide.addEventListener("click", () => {
  if (!isWeekComplete(state.selectedWeek)) {
    return;
  }

  let guide = guideForWeek(state.selectedWeek);
  if (!guide) {
    guide = buildReviewGuide(state.selectedWeek);
    state.reviewGuides = state.reviewGuides.filter((item) => item.week !== state.selectedWeek);
    state.reviewGuides.push(guide);
    saveReviewGuides();
    renderSavedReviewGuides();
    renderReviewStatus();
  }

  openReviewGuide(guide);
});

document.querySelector("#resetProgress").addEventListener("click", () => {
  state.progress = {};
  saveProgress();
  render();
});

document.querySelector("#markToday").addEventListener("click", () => {
  const subject = subjects.find((item) => item.id === state.selectedSubject);
  state.progress[taskKey(state.selectedWeek, subject.id)] = true;
  saveProgress();
  render();
});

document.querySelector("#projectButton").addEventListener("click", () => {
  const index = Math.min(Math.floor((state.selectedWeek - 1) / 2), challengeIdeas.length - 1);
  const [title, text] = challengeIdeas[index];
  document.querySelector("#challengeTitle").textContent = title;
  document.querySelector("#challengeText").textContent = text;
  document.querySelector("#challengeDialog").showModal();
});

render();
