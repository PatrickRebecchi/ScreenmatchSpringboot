// URL base da API
// const baseURL = 'http://localhost:8080';

//export default function getDados(endpoint) {
  //  return fetch(`${baseURL}${endpoint}`)
     //   .then(response => response.json())
   //     .catch(error => {
 //           console.error('Erro ao acessar o endpoint /series/top5:', error);
//        });
//}

// URL base da API (produção)
const baseURL = 'https://screenmatchspringboot.onrender.com';

export default function getDados(endpoint) {
    return fetch(`${baseURL}${endpoint}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }
            return response.json();
        })
        .catch(error => {
            console.error('Erro ao acessar API:', error);
        });
}
