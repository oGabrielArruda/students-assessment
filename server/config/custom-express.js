const express = require('express');
const app = express();
const bodyParser = require('body-parser');

// configurando o body parser para pegar POSTS mais tarde
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const routes = require('../app/routes');
routes(app);

module.exports = app;