(ns naive-bayes.core)

(defn count-classes
  [samples]
  (->>
    (group-by second samples)
    (map (fn [x] [(key x) (count (val x))]))
    (reduce (fn [a x] (conj a {(first x) (second x)})) {})))

(defn unreduce-feature
  [entry]
  (let [feats (first entry)
        class (second entry)]
    (->> feats
      (map (fn [x] [class x])))))

(defn count-features
  [entries]
  (frequencies
    (->> entries
      (map unreduce-feature)
      (reduce concat))))

(defn map-kv [f coll]
  (reduce-kv (fn [m k v] (assoc m k (f [k v]))) (empty coll) coll))

(defn features-result
  [features]
  (map-kv
    (fn [e]
      (/ (second e) ((count-classes features) (first (first e)))))
  (count-features features)))

(defn classes-result
  [samples]
  (map-kv
    (fn [e] (/ (second e) (count samples))) 
    (count-classes samples)))

(defn train
  [features]
  {:classes (classes-result features)
   :freq (features-result features)})

(defn class-value
  [classifier feats cl]
  (let [classes (classifier :classes)
        prob (classifier :freq)]
    (+
      (- (Math/log (classes cl)))
      (->> (keys feats)
        (map
	  (fn [feat]
	    (-
	      (Math/log
	        (prob [cl [feat (feats feat)]]
	        (Math/pow 10 (- 7)))))))
        (reduce +)))))

(defn mycmp [a b]
  (compare (second a) (second b)))

(defn classify
  [classifier item]
  (let [classes (classifier :classes)
        prob (classifier :freq)]
    (->> (keys classes)
      (map (fn [c] [c (class-value classifier item c)]))
      (sort mycmp)
      (ffirst))))

