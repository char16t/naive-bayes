# naive-bayes

Naive Bayes classifier for Clojure.

## Usage

```
(defn long-str [& strings]
  (clojure.string/join "\n" strings))

(def data
  (long-str
    "Иван Мужской"
    "Пётр Мужской"
    "Валерий Мужской"
    "Александр Мужской"
    "Алёна Женский"
    "Мария Женский"
    "Маргарита Женский"
    "Александра Женский"))

(def samples
  (->>
    (clojure.string/split-lines data)
    (map #(clojure.string/split % #"\s"))))

(defn get-features [sample]
  {:last-letter (last sample)
   :first-letter (first sample)
   :second-letter (second sample)})

(def features
  (->> samples
    (map (fn [w] [(get-features (first w)) (second w)]))))

(def classifier
  (train features))

(println
  (classify classifier (get-features "Агафья")))
;; => Женский
```

## License

Copyright © 2018 Valeriy Manenkov

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
