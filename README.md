# naive-bayes

Naive Bayes classifier for Clojure.

## Usage example

```clojure
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

MIT
