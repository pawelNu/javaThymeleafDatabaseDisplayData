$(document).ready(function () {
  var select2 = $(".select2").select2({
    width: "300px",
  });

  var addedWordsContainer = $("#addedWordsContainer");
  var addWordButton = $("#addWordButton");
  var wordInput = $("#field1");
  var hiddenField = $("#hiddenField");

  addWordButton.on("click", function () {
    addWord();
  });

  wordInput.on("keydown", function (event) {
    if (event.key === "Enter") {
      event.preventDefault();
      addWord();
    }
  });

  function addWord() {
    var word = wordInput.val().trim();
    if (word !== "") {
      var addedWord = $('<div class="added-word"></div>').text(word);
      var removeButton = $('<span class="remove-word">&#x2716;</span>');
      removeButton.on("click", function () {
        $(this).parent().next("br").remove(); // Usuń <br>
        $(this).parent().remove(); // Usuń słowo kluczowe
        updateHiddenField(); // Zaktualizuj ukryte pole formularza po usunięciu słowa
      });
      addedWord.append(removeButton);
      addedWordsContainer.append(addedWord);
      addedWordsContainer.append("<br>"); // Dodaj nową linię po dodaniu słowa kluczowego
      wordInput.val("");

      updateHiddenField(); // Zaktualizuj ukryte pole formularza po dodaniu słowa
    }
  }

  function updateHiddenField() {
    var words = addedWordsContainer.find(".added-word").map(function () {
      return $(this).text().replace('✖', ''); // Usuń znak "✖" z wartości
    }).get();

    hiddenField.val(words.join(","));
  }
});
