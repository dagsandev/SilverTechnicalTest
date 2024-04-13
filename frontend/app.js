const API_URL = "http://localhost:8080/api/v1/";

const tableBody = document.querySelector(".table tbody");
const productForm = document.getElementById("product-form");
const allproductsBtn = document.getElementById("allProductsBtn");
const updateBtn = document.getElementById("updateBtn");
const saveBtn = document.getElementById("saveBtn");
const allBtnEdit = document.querySelectorAll(".btn-edit");
let productId;



function updateTable(data) {
  tableBody.innerHTML = "";

  const categoryNames = {
    1: "Footwear",
    2: "Garden",
    3: "Kitchen",
    4: "Entertainment",
  };

  data.forEach((product) => {
    const rowData = `
            <tr>
                <td>${product.id}</td> 
                <td>${product.name}</td>
                <td>${product.brand}</td>
                <td>${product.stock}</td>
                <td>${product.price}</td>
                <td>${categoryNames[product.categoryId]}</td>
                
                <td>
                  <button class="btn-delete btn btn-danger" data-product-id="${product.id}"> Delete </button>
                </td>

                <td>
                  <button class="btn-edit btn btn-primary" data-product-id="${product.id}"> Edit </button>
                </td>
            </tr>
        `;

    tableBody.innerHTML += rowData;
  });
}

function resetForm() {
  document.getElementById("product-form").reset();
}

function showMsg(msg, cssClass) {
  const div = document.createElement("div");
  div.className = `alert alert-${cssClass} m2-2`;
  div.appendChild(document.createTextNode(msg));

  const messageContainer = document.getElementById("message-container");
  messageContainer.appendChild(div);

  setTimeout(function () {
    div.remove();
  }, 3000);
}

//product form
productForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const name = document.getElementById("name").value;
  const brand = document.getElementById("brand").value;
  const stock = document.getElementById("stock").value;
  const price = document.getElementById("price").value;
  const categoryId = document.getElementById("categoryId").value;

  const productData = { name, brand, stock, price, categoryId };

  if (saveBtn.classList.contains("d-none")) {
    updateProduct(productId, productData);
  } else {
    saveProduct(productData);
  }
  resetForm();
});

function saveProduct(productData) {
  fetch(API_URL + "products/", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(productData),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error occurred while attempting to save the product.");
      }
      showMsg("Product saved succesfully", "success");
      getAllProducts();
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

function updateProduct(id, productData) {
  fetch(`${API_URL}products/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(productData),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(
          "Error occurred while attempting to update the product."
        );
      }
      showMsg("Product updated succesfully", "success");
      getAllProducts();
    })
    .catch((error) => {
      console.error("Error:", error);
    });
}

//editBtn function
document.addEventListener("click", function (e) {
  if (e.target && e.target.classList.contains("btn-edit")) {
    productId = e.target.getAttribute("data-product-id");

    saveBtn.classList.add("d-none");
    updateBtn.classList.remove("d-none");
  }
});

//deleteBtn function
document.addEventListener("click", function (event) {
  if (event.target && event.target.classList.contains("btn-delete")) {
    if (confirm("Are you sure you want to delete?")) {
      let id = event.target.getAttribute("data-product-id");

      fetch(API_URL + "products/" + id, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(
              "Error occurred while attempting to delete the product."
            );
          }

          showMsg("Product deleted successfully", "danger");
          getAllProducts();
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    }
  }
});

//FUNCTION PRODUCTS REQUEST
function getAllProducts() {
  fetch(`${API_URL}products/`)
    .then((response) => response.json())
    .then((products) => updateTable(products))
    .catch((error) => console.error("Error fetching all products:", error));
}

allproductsBtn.onclick = () => {
  saveBtn.classList.remove("d-none");
  updateBtn.classList.add("d-none");
  getAllProducts();
};

function getProductByName(text) {
  fetch(`${API_URL}products/name?name=${text}`)
    .then((response) => response.json())
    .then((products) => updateTable(products))
    .catch((error) => console.error("Error fetching products by name:", error));
}

function getProductByBrand(brand) {
  fetch(`${API_URL}products/brand?brand=${brand}`)
    .then((response) => response.json())
    .then((products) => updateTable(products))
    .catch((error) =>
      console.error("Error fetching products by brand:", error)
    );
}

function getProductByCategory(category) {
  fetch(`${API_URL}categories/name?name=${category}`)
    .then((response) => response.json())
    .then((products) => updateTable(products))
    .catch((error) =>
      console.error("Error fetching products by category name", error)
    );
}

//PRODUCT FINDER
//finder execution
document.querySelector(".btnFinder").addEventListener("click", function () {
  const selectorValue = document.getElementById("findProductSelector").value;
  const userInputValue = document.querySelector(".searchInput").value;

  switch (selectorValue) {
    case "name":
      getProductByName(userInputValue);
      break;
    case "brand":
      getProductByBrand(userInputValue);
      break;
    case "category":
      getProductByCategory(userInputValue);
      break;
    default:
      console.error("Something went wrong.");
  }
});
