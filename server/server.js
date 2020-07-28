app = require('./config/custom-express')

const porta = 3000 || process.env.port;

app.listen(3000, () => {
    console.log("Executando!");
});