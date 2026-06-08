const usersStorageKey = "gedsUsers";
const sessionStorageKey = "gedsSessionUser";

function getUsers() {
  return JSON.parse(localStorage.getItem(usersStorageKey) || "[]");
}

function saveUsers(users) {
  localStorage.setItem(usersStorageKey, JSON.stringify(users));
}

document.querySelectorAll("[data-toggle-password]").forEach((button) => {
  button.addEventListener("click", () => {
    const input = button.closest(".password-field").querySelector("[data-password-input]");
    const showing = input.type === "text";
    input.type = showing ? "password" : "text";
    button.textContent = showing ? "Ver" : "Ocultar";
  });
});

document.querySelectorAll("[data-auth-form]").forEach((form) => {
  form.addEventListener("submit", (event) => {
    event.preventDefault();
    const status = form.closest("section").querySelector("[data-form-status]") || document.querySelector("[data-form-status]");
    const data = Object.fromEntries(new FormData(form).entries());
    const users = getUsers();
    const isRegister = Boolean(data.name);

    if (isRegister) {
      if (users.some((user) => user.email.toLowerCase() === data.email.toLowerCase())) {
        status.textContent = "Este correo ya esta registrado. Inicia sesion.";
        return;
      }
      const user = { name: data.name, email: data.email, password: data.password, role: data.role || "planner", createdAt: new Date().toISOString() };
      users.push(user);
      saveUsers(users);
      localStorage.setItem(sessionStorageKey, JSON.stringify({ name: user.name, email: user.email, role: user.role }));
      status.textContent = "Cuenta creada. Te llevaremos al catalogo.";
      setTimeout(() => { window.location.href = "catalogo.html"; }, 800);
      return;
    }

    const user = users.find((item) => item.email.toLowerCase() === data.email.toLowerCase() && item.password === data.password);
    if (!user) {
      status.textContent = "Correo o password incorrecto. Puedes crear una cuenta nueva.";
      return;
    }

    localStorage.setItem(sessionStorageKey, JSON.stringify({ name: user.name, email: user.email, role: user.role }));
    status.textContent = "Sesion iniciada. Te llevaremos al catalogo.";
    setTimeout(() => { window.location.href = "catalogo.html"; }, 800);
  });
});
