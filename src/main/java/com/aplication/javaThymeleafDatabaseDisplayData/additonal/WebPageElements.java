package com.aplication.javaThymeleafDatabaseDisplayData.additonal;

import lombok.Getter;

@Getter
public class WebPageElements {
    // class with web page elements
    // used to build a page with the results of a database query
    private String head1 = """
<!DOCTYPE html>
<html>
<head>
	<title>Ticket history</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

  <style>
    body {
      font-family: Arial, sans-serif;
      font-size: 16px;
      line-height: 1.5;
      margin: 0;
      padding: 0;
    }

    h3 {
      position: fixed;
      background-color: rgb(243, 243, 243);
      max-width: 800px;
    }

    .container {
      max-width: 800px;
      margin: 0 auto;
      padding: 5px;
    }
  </style>

</head>
""";

    private String body1 = """
<body>
	<div class="container">
""";

    private String body2 = """
	</div>
</body>
</html>
""";

}
